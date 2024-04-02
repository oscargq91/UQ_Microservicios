Feature: API de gestión de usuarios

    Background:
        Given la API está ejecutándose en 'http://localhost:8080/api/v1'

    Scenario: Crear un nuevo usuario con éxito
        When se envía una solicitud POST a '/users' con los siguientes datos:
        {
        "user": {
        "username": "prueba5",
        "password": "123456",
        "email": "prueba5@prueba.com"
        }
        }
        Then la respuesta debe tener un código de estado 201
        And la respuesta debe contener los detalles del usuario creado
        And el ID del usuario debe estar presente en la respuesta

    Scenario: Intentar crear un usuario con un username que ya existe
        When se envía una solicitud POST a '/users' con los siguientes datos:
        {
        "user": {
        "username": "usuario_existente",
        "password": "123456",
        "email": "usuario_existente@example.com"
        }
        }
        Then la respuesta debe tener un código de estado 409
        And la respuesta debe contener un mensaje de error indicando que el username ya existe

    Scenario: Intentar crear un usuario sin especificar el campo username
        When se envía una solicitud POST a '/users' con los siguientes datos:
        {
        "user": {
        "password": "123456",
        "email": "prueba_sin_username@prueba.com"
        }
        }
        Then la respuesta debe tener un código de estado 400
        And la respuesta debe contener un mensaje de error indicando que el campo username es obligatorio

    Scenario: Intentar crear un usuario sin especificar el campo password
        When se envía una solicitud POST a '/users' con los siguientes datos:
        {
        "user": {
        "username": "usuario_sin_password",
        "email": "usuario_sin_password@prueba.com"
        }
        }
        Then la respuesta debe tener un código de estado 400
        And la respuesta debe contener un mensaje de error indicando que el campo password es obligatorio

    Scenario: Intentar crear un usuario sin especificar el campo email
        When se envía una solicitud POST a '/users' con los siguientes datos:
        {
        "user": {
        "username": "usuario_sin_email",
        "password": "123456"
        }
        }
        Then la respuesta debe tener un código de estado 400
        And la respuesta debe contener un mensaje de error indicando que el campo email es obligatorio


    Scenario: Listar usuarios exitosamente
        When se envía una solicitud GET a '/users' con los siguientes headers:
            | Nombre del Header | Valor |
            | page-size         | 0     |
            | page-number       | 0     |
        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener una lista de usuarios

    Scenario: Listar usuarios con paginación
        When se envía una solicitud GET a '/users' con los siguientes headers:
            | Nombre del Header | Valor |
            | page-size         | 10    |
            | page-number       | 1     |
        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener una lista de usuarios paginada
        And el número de usuarios en la página debe ser igual al tamaño de página especificado
        And la respuesta debe contener información de paginación

    Scenario: Listar usuarios sin especificar el tamaño de página ni el número de página
        When se envía una solicitud GET a '/users' sin especificar el tamaño de página ni el número de página
        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener una lista de usuarios
        And la lista de usuarios no debe ser paginada

    Scenario: Eliminar un usuario existente
        When se envía una solicitud DELETE a '/users/id_usuario'
        Then la respuesta debe tener un código de estado 204

    Scenario: Obtener un usuario existente por su ID
        When se envía una solicitud GET a '/users/65f3947bb3c3d10286754f5a'
        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener los detalles del usuario
        And el ID del usuario en la respuesta debe ser '65f3947bb3c3d10286754f5a'

    Scenario: Intentar obtener un usuario que no existe por su ID
        When se envía una solicitud GET a '/users/usuario_no_existente'
        Then la respuesta debe tener un código de estado 404
        And la respuesta debe contener un mensaje de error indicando que el usuario no fue encontrado

    Scenario: Actualizar un usuario existente
        When se envía una solicitud PUT a '/users' con los siguientes datos:
        {
        "user": {
        "id": "65f3947bb3c3d10286754f5a",
        "username": "nuevo_nombre_de_usuario",
        "password": "nueva_contraseña",
        "email": "nuevo_email@prueba.com"
        }
        }

        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener los detalles actualizados del usuario
        And el ID del usuario en la respuesta debe ser '65f3947bb3c3d10286754f5a'
        And los detalles del usuario en la respuesta deben ser los actualizados

    Scenario: Intentar actualizar un usuario que no existe
        When se envía una solicitud PUT a '/users' con los siguientes datos:
        {
        "user": {
        "id": "usuario_no_existente",
        "username": "nuevo_nombre_de_usuario",
        "password": "nueva_contraseña",
        "email": "nuevo_email@prueba.com"
        }
        }
        Then la respuesta debe tener un código de estado 404
        And la respuesta debe contener un mensaje de error indicando que el usuario no fue encontrado

    Scenario: Intentar crear un usuario sin especificar el campo username
        When se envía una solicitud PUT a '/users' con los siguientes datos:
        {
        "user": {
        "id": "usuario_no_existente",
        "password": "123456",
        "email": "prueba_sin_username@prueba.com"
        }
        }
        Then la respuesta debe tener un código de estado 400
        And la respuesta debe contener un mensaje de error indicando que el campo username es obligatorio

    Scenario: Intentar crear un usuario sin especificar el campo password
        When se envía una solicitud PUT a '/users' con los siguientes datos:
        {
        "user": {
        "id": "usuario_no_existente",
        "username": "usuario_sin_password",
        "email": "usuario_sin_password@prueba.com"
        }
        }
        Then la respuesta debe tener un código de estado 400
        And la respuesta debe contener un mensaje de error indicando que el campo password es obligatorio

    Scenario: Intentar crear un usuario sin especificar el campo email
        When se envía una solicitud PUT a '/users' con los siguientes datos:
        {
        "user": {
        "id": "usuario_no_existente",
        "username": "usuario_sin_email",
        "password": "123456"
        }
        }
        Then la respuesta debe tener un código de estado 400
        And la respuesta debe contener un mensaje de error indicando que el campo email es obligatorio

    Scenario: Iniciar sesión con éxito
        When se envía una solicitud POST a /users/login con los siguientes datos:
        {
        "login": {
        "username": "prueba1",
        "password": "123456"
        }
        }
        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener un token de acceso

    Scenario: Intentar iniciar sesión con un username no existente
        When se envía una solicitud POST a /users/login con un username no existente
        Then la respuesta debe tener un código de estado 404
        And la respuesta debe contener un mensaje de error indicando que el usuario no fue encontrado

    Scenario: Intentar iniciar sesión con una contraseña incorrecta
        When se envía una solicitud POST a /users/login con una contraseña incorrecta
        Then la respuesta debe tener un código de estado 401
        And la respuesta debe contener un mensaje de error indicando que la contraseña es incorrecta

    Scenario: Actualizar contraseña con éxito
        Given se tiene un token de acceso válido
        When se envía una solicitud PUT a /users/password con el siguiente token y datos:
        {
        "password": "123456789"
        }
        Then la respuesta debe tener un código de estado 200
        And la respuesta debe contener los detalles actualizados del usuario

    Scenario: Intentar actualizar contraseña con un token vencido
        Given se tiene un token de acceso vencido
        When se envía una solicitud PUT a /users/password con un token vencido
        Then la respuesta debe tener un código de estado 401
        And la respuesta debe contener un mensaje de error indicando que el token está vencido

    Scenario: Intentar actualizar contraseña con un token malformado
        Given se tiene un token de acceso malformado
        When se envía una solicitud PUT a /users/password con un token malformado
        Then la respuesta debe tener un código de estado 401
        And la respuesta debe contener un mensaje de error indicando que el token está malformado

