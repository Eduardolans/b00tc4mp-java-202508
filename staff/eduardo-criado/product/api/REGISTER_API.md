# API de Registro de Usuarios

## Endpoint: POST /api/register

Este endpoint permite registrar nuevos usuarios en el sistema.

### URL
```
POST http://localhost:8080/api/register
```

### Request Body (JSON)
```json
{
  "name": "Juan Pérez",
  "username": "juanperez",
  "password": "miPassword123"
}
```

### Respuestas

#### Éxito (201 Created)
```json
{
  "success": true,
  "message": "User registered successfully"
}
```

#### Error - Usuario duplicado (409 Conflict)
```json
{
  "success": false,
  "error": "DuplicityException",
  "message": "user already exists"
}
```

#### Error - Error del servidor (500 Internal Server Error)
```json
{
  "success": false,
  "error": "InternalServerError",
  "message": "Error message here"
}
```

### Ejemplo con curl

```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "username": "juanperez",
    "password": "miPassword123"
  }'
```

### Cómo ejecutar la API

1. Compilar el proyecto:
```bash
cd /home/eddy-c/workspace/b00tc4mp-java-202508/staff/eduardo-criado/product/api
mvn clean package
```

2. Ejecutar con Jetty:
```bash
mvn jetty:run
```

3. O desplegar en Tomcat usando el script:
```bash
./deploy.sh
```

La API estará disponible en `http://localhost:8080/api`
