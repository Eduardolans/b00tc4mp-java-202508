# API de Registro de Usuarios

## Endpoint: POST /users

Este endpoint permite registrar nuevos usuarios en el sistema.

### URL
```
POST http://localhost:8080/api/users
```

### Request Body (JSON)
```json
{
  "name": "Juan Pérez",
  "username": "juanperez",
  "password": "miPassword123"
}
```

### Campos Requeridos

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `name` | String | Nombre completo del usuario |
| `username` | String | Nombre de usuario único |
| `password` | String | Contraseña del usuario |

### Respuestas

#### Éxito (201 Created)

Usuario registrado exitosamente. No retorna body, solo el código de estado.

```
HTTP/1.1 201 Created
```

#### Error - Usuario duplicado (409 Conflict)

El username ya existe en el sistema.

```json
{
  "success": false,
  "error": "DuplicityException",
  "message": "user already exists"
}
```

#### Error - Error del servidor (500 Internal Server Error)

Error interno del servidor.

```json
{
  "success": false,
  "error": "InternalServerError",
  "message": "Error message here"
}
```

### Ejemplo con curl

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "username": "juanperez",
    "password": "miPassword123"
  }'
```

### Ejemplo con test bash

```bash
# Ejecutar el script de test
./src/test/bash/register-user.sh
```

### Notas Importantes

- ⚠️ **No requiere autenticación** - Este es un endpoint público
- ✅ El username debe ser único
- 🔒 Las contraseñas se almacenan en texto plano (solo para desarrollo, en producción usar bcrypt o similar)
- 📝 Después de registrarse, el usuario debe autenticarse en `/users/auth` para obtener un token JWT

### Flujo Completo

1. **Registrar usuario** con `POST /users`
2. **Autenticarse** con `POST /users/auth` para obtener token JWT
3. **Usar el token** en endpoints protegidos (`/users/all`, `/users/info`)

### Ver También

- [API_ENDPOINTS.md](API_ENDPOINTS.md) - Documentación completa de todos los endpoints
- [README.md](README.md) - Información general del proyecto

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
