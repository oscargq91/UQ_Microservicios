# Establecer la imagen base
FROM python:3.9

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el contenido del directorio actual al directorio de trabajo del contenedor
COPY . .

# Instalar las dependencias de la aplicación
RUN pip install --no-cache-dir -r requirements.txt

# Exponer el puerto en el que la aplicación Flask se ejecutará
EXPOSE 8050

# Comando para ejecutar la aplicación Flask
CMD ["python", "app.py"]