
from flask import Flask, request, jsonify
from pymongo import MongoClient
from apscheduler.schedulers.background import BackgroundScheduler
import requests
import logging
from logging.handlers import RotatingFileHandler
from flask_mail import Mail, Message

app = Flask(__name__)

# Configuración de la base de datos MongoDB
client = MongoClient('mongodb://my-mongo-db:27017/')
db = client['mi_base_de_datos']
services_collection = db['services']

# Configuración del scheduler
scheduler = BackgroundScheduler()
scheduler.start()

# Lista para almacenar los servicios
services = []

# Configuración de registro de eventos
formatter = logging.Formatter("[%(asctime)s] [%(levelname)s] %(message)s")
handler = RotatingFileHandler('health_monitor.log', maxBytes=10000, backupCount=1)
handler.setFormatter(formatter)
app.logger.addHandler(handler)
app.logger.setLevel(logging.INFO)

# Configuración de correo electrónico
app.config['MAIL_SERVER'] = 'smtp.gmail.com'
app.config['MAIL_PORT'] = 587
app.config['MAIL_USERNAME'] = 'oegarcesq@uqvirtual.edu.co'
app.config['MAIL_PASSWORD'] = 'Homero140491'
app.config['MAIL_USE_TLS'] = True
app.config['MAIL_USE_SSL'] = False
mail = Mail(app)


def load_services():
    global services
    services = list(services_collection.find())


def update_scheduler():
    global services
    scheduler.remove_all_jobs()

    for service in services:
        scheduler.add_job(check_service_health, 'interval', seconds=get_frequency_seconds(service['frequency']),
                          args=[service], id=service['name'])


def get_frequency_seconds(frequency):
    interval, unit = frequency[:-1], frequency[-1]
    if unit == 's':
        return int(interval)
    elif unit == 'm':
        return int(interval) * 60
    elif unit == 'h':
        return int(interval) * 3600
    elif unit == 'd':
        return int(interval) * 86400
    else:
        raise ValueError(f"Invalid frequency unit: {unit}")


def check_service_health(service):
    try:
        response = requests.get(service['endpoint'])
        if response.status_code == 200:
            service['status'] = 'Healthy'
            app.logger.info("Service health check")
        else:
            service['status'] = 'Unhealthy'
            app.logger.info("Service health check Failed")
            send_notification(service)
    except Exception as e:
        service['status'] = 'Unhealthy'
        app.logger.error(f"Error checking service {service['name']}: {e}")
        send_notification(service)


def send_notification(service):
    with app.app_context():
        for recipient in service['notification_emails']:
            msg = Message("Health Check Alert",
                          sender="oegarcesq@uqvirtual.edu.co",
                          recipients=[recipient])
            msg.body = f"The service {service['name']} is {service['status']}. Please investigate."
            mail.send(msg)


@app.route('/register', methods=['POST'])
def register_service():
    data = request.json
    service_name = data.get('name')
    existing_service = services_collection.find_one({'name': service_name})
    if existing_service:
        return jsonify({'error': f'Service with name {service_name} already exists'}), 400
    else:
        services_collection.insert_one(data)
        app.logger.info(f"Service {data['name']} registered successfully")
        load_services()  # Actualizar la lista de servicios
        update_scheduler()  # Actualizar el programador de tareas
        return jsonify({'message': 'Service registered successfully'}), 201


@app.route('/services', methods=['GET'])
def get_all_services():
    # Obtener una lista de diccionarios de los servicios
    service_dicts = [service for service in services_collection.find({}, {'_id': False})]
    return jsonify(service_dicts), 200


@app.route('/delete/<service_name>', methods=['DELETE'])
def delete_service(service_name):
    result = services_collection.delete_one({'name': service_name})
    if result.deleted_count > 0:
        load_services()
        update_scheduler()
        return jsonify({'message': f'Service {service_name} deleted successfully'}), 200
    else:
        return jsonify({'message': 'Service not found'}), 404


@app.route('/health/<service_name>', methods=['GET'])
def get_health(service_name):
    service = next((s for s in services if s['name'] == service_name), None)
    if service:
        try:
            response = requests.get(service['endpoint'])
            if response.status_code == 200:
                return jsonify(response.json()), 200
            else:
                return jsonify({'message': 'Service not available'}), 503
        except Exception as e:
            return jsonify({'message': f'Error checking service health: {str(e)}'}), 503
    else:
        return jsonify({'message': 'Service not found'}), 404


if __name__ == '__main__':
    load_services()
    update_scheduler()
    app.run(debug=False, host='0.0.0.0', port=8050)
