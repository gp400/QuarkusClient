# Descripcion general
Esta API se encarga de realizar un CRUD de clientes, teniendo los campos primer nombre, segundo nombre, primer apellido, correo, dirección, teléfono, país (código ISO 3166) y gentilicio; Siendo los unicos campos opcionales, segundo nombre y segundo apellido. El gentilicio se obtiene de una API de terceros basado en el país enviado. Los datos son persistidos en una base de datos postgres.

# QuickStart
1. Ejecuta `docker compose up -d` para crear la base de datos.
2. Ejecuta `./mvnw quarkus:dev` para correr el projecto. Por defecto toma el puerto 8080.

# Endpoints
## GET /Client
 - Retorna un 200 todos los clientes existentes en la base de datos

## GET /Client/{id}
 - Retorna un 200 con el cliente en cuestion
 - Retorna un 404 si no encuentra un cliente con el id enviado

## GET /Client/Pais/{pais}
 - Retorna un 200 con todos los clientes que tengan el país enviado

## POST /Client
 - Retorna un 201 cuando el cliente se creo correctamente
 - Retorna un 400 si el json enviado no es valido
 - Retorna un 400 si el pais no es valido

## PUT /Client
 - Retorna un 200 cuando el cliente se edito correctamente
 - Retorna un 400 si el json enviado no es valido
 - Retorna un 400 si el pais no es valido
 - Retorna un 404 si no encuentra un cliente con el id evniado

## DELETE /Client/{id}
 - Retorna un 200
 - Retorna un 404 si no encuentra un cliente con el id enviado

# Ejemplos cURL
## GET /Client
curl --location 'http://localhost:8080/Client' \
--data ''

## GET /Client/{id}
curl --location 'http://localhost:8080/Client/1' \
--data ''

## GET /Client/Pais/{pais}
curl --location 'http://localhost:8080/Client/Pais/do' \
--data ''

## POST /Client
curl --location 'http://localhost:8080/Client' \
--header 'Content-Type: application/json' \
--data-raw '{
    "PrimerNombre": "PrimerNombre",
    "SegundoNombre": "SegundoNombre",
    "PrimerApellido": "PrimerApellido",
    "SegundoApellido": "SegundoApellido",
    "Correo": "correo@gmail.com",
    "Direccion": "Direccion",
    "Telefono": "829-555-5555",
    "Pais": "do"
}'

## PUT /Client
curl --location --request PUT 'http://localhost:8080/Client' \
--header 'Content-Type: application/json' \
--data-raw '{
    "Id": 1,
    "Correo": "correo@gmail.com",
    "Direccion": "Direccion",
    "Telefono": "829-555-5555",
    "Pais": "do"
}'

## DELETE /Client/{id}
curl --location --request DELETE 'http://localhost:8080/Client/52' \
--data ''

# Manejo de errores
En el package providers agregue el archivo `GlobalExceptionHandler` que se encarga de manejar todos los errores que puedan suceder en el proyecto y retornar un status code basado en dicho error. El status code por defecto es 400.

# Validaciones
En el package providers agregue el archivo `ConstraintViolationMapper` que se encarga de formatear los errores retornados por el @Valid cuando el DTO no es valido. Ejemplo: 
[
    {
        "field": "CampoInvalido",
        "message": "Mensaje de error"
    }
]