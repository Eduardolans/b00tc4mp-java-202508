# Documentación de la API - Endpoints Disponibles

## Base URL
```
http://localhost:8080/api
```

---

## 1. Registro de Usuario

### `POST /users`

Registra un nuevo usuario en el sistema.

**Request Body:**
```json
{
  "name": "Juan Pérez",
  "username": "juanperez",
  "password": "miPassword123"
}
```

**Respuestas:**

**201 Created** - Usuario registrado exitosamente (sin body)
```
HTTP/1.1 201 Created
```

**409 Conflict** - Usuario ya existe
```json
{
  "success": false,
  "error": "DuplicityException",
  "message": "user already exists"
}
```

**500 Internal Server Error** - Error del servidor
```json
{
  "success": false,
  "error": "InternalServerError",
  "message": "Error message"
}
```

**Ejemplo curl:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","username":"juanperez","password":"pass123"}'
```

---

## 2. Autenticación (Login)

### `POST /users/auth`

Autentica al usuario y devuelve un token JWT para autenticación en peticiones subsecuentes.

**Request Body:**
```json
{
  "username": "juanperez",
  "password": "miPassword123"
}
```

**Respuestas:**

**200 OK** - Autenticación exitosa
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFucGVyZXoiLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDA4NjQwMH0.abc123..."
}
```

**401 Unauthorized** - Credenciales inválidas
```json
{
  "error": "CredentialsException",
  "message": "invalid credentials"
}
```

**Ejemplo curl:**
```bash
curl -X POST http://localhost:8080/api/users/auth \
  -H "Content-Type: application/json" \
  -d '{"username":"juanperez","password":"pass123"}'
```

**Nota:** El token devuelto debe incluir el prefijo `Bearer ` al usarlo en el header `Authorization`. La aplicación cliente debe agregar este prefijo al guardar o enviar el token.

---

## 3. Obtener Información de Usuario

### `GET /users/info`

Obtiene los datos del usuario autenticado según el token JWT. **Requiere autenticación JWT.**

**Headers requeridos:**
- `Authorization: Bearer <token>` - Token JWT obtenido al autenticarse

**Respuestas:**

**200 OK** - Datos del usuario
```json
{
  "name": "Juan Pérez",
  "username": "juanperez"
}
```

**401 Unauthorized** - Token inválido o expirado
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

**404 Not Found** - Usuario no encontrado
```json
{
  "error": "NotFoundException",
  "message": "user not found"
}
```

**Ejemplo curl:**
```bash
curl -X GET http://localhost:8080/api/users/info \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 4. Obtener Todos los Usuarios

### `GET /users/all`

Obtiene la lista de todos los usuarios registrados en el sistema. **Requiere autenticación JWT.**

**Headers requeridos:**
- `Authorization: Bearer <token>` - Token JWT obtenido al autenticarse

**Respuestas:**

**200 OK** - Lista de usuarios (array directo)
```json
[
  {
    "name": "Juan Pérez",
    "username": "juanperez"
  },
  {
    "name": "María García",
    "username": "mariagarcia"
  }
]
```

**401 Unauthorized** - Token inválido o expirado
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

**404 Not Found** - Usuario no encontrado
```json
{
  "error": "NotFoundException",
  "message": "user not found"
}
```

**Nota:** Por seguridad, las contraseñas NO se incluyen en la respuesta.

**Ejemplo curl:**
```bash
curl -X GET http://localhost:8080/api/users/all \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 5. Reset de Datos (Solo Testing)

### `POST /test/reset`

**⚠️ SOLO PARA TESTING** - Elimina todos los usuarios de la memoria. No debe usarse en producción.

**Uso:**
```bash
curl -X POST http://localhost:8080/api/test/reset \
  -H "Content-Type: application/json"
```

**O usando el script:**
```bash
cd api
./src/test/bash/reset-data.sh
```

**Respuestas:**

**200 OK** - Datos reseteados exitosamente
```json
{
  "message": "Data reset successfully"
}
```

**500 Internal Server Error** - Error al resetear
```json
{
  "success": false,
  "error": "InternalServerError",
  "message": "Error message"
}
```

### ¿Cuándo usar este endpoint?

1. **Antes de ejecutar tests de integración** - Para asegurar un estado limpio
2. **Entre ejecuciones de tests** - Para evitar que se acumulen datos de tests anteriores
3. **Al desarrollar** - Para resetear rápidamente la API sin reiniciarla

### Ejemplo de flujo de testing:

```bash
# 1. Resetear datos antes de ejecutar tests
cd api
./src/test/bash/reset-data.sh

# 2. Ejecutar tests de la app con datos limpios
cd ../app
mvn test
```

### Notas de seguridad:

⚠️ **IMPORTANTE**: Este endpoint:
- Solo debe usarse en entornos de desarrollo/testing
- No requiere autenticación (por simplicidad en testing)
- Elimina TODOS los usuarios de la memoria
- **NO debe estar disponible en producción**

---

## Autenticación JWT

La API usa **JSON Web Tokens (JWT)** para autenticación:

### ¿Cómo funciona?

1. **Autenticación:** El cliente envía credenciales a `POST /users/auth`
2. **Token:** El servidor valida las credenciales y devuelve un token JWT
3. **Almacenamiento:** El cliente guarda el token (en localStorage, memoria, etc.)
4. **Uso:** El cliente incluye el token en el header `Authorization: Bearer <token>` en cada petición
5. **Validación:** El servidor valida el token en cada petición protegida
6. **Expiración:** Los tokens expiran después de 24 horas

### Endpoints que requieren autenticación JWT:
- `GET /users/all` - Obtener todos los usuarios
- `GET /users/info` - Obtener información de usuario

### Endpoints públicos (no requieren autenticación):
- `POST /users` - Registro de usuario
- `POST /users/auth` - Autenticación (login)
- `POST /test/reset` - Reset de datos (solo testing)

### Ventajas de JWT:
- ✅ Stateless - No requiere almacenar sesiones en el servidor
- ✅ Escalable - Funciona en múltiples servidores
- ✅ Compatible con aplicaciones web y móviles
- ✅ Fácil de integrar con aplicaciones frontend (localStorage)

---

## Flujo de Trabajo Recomendado

### Flujo completo con JWT:

1. **Registrar usuario:**
   ```bash
   curl -X POST http://localhost:8080/api/users \
     -H "Content-Type: application/json" \
     -d '{"name":"Juan Pérez","username":"juanperez","password":"pass123"}'
   ```

2. **Autenticarse y obtener token:**
   ```bash
   curl -X POST http://localhost:8080/api/users/auth \
     -H "Content-Type: application/json" \
     -d '{"username":"juanperez","password":"pass123"}'
   ```
   Respuesta: `{"token":"eyJhbGci..."}`

3. **Guardar el token con prefijo "Bearer "** en localStorage (web) o en memoria (app)

4. **Usar el token en peticiones protegidas:**
   ```bash
   # Obtener info del usuario autenticado
   curl -X GET http://localhost:8080/api/users/info \
     -H "Authorization: Bearer eyJhbGci..."

   # Obtener todos los usuarios
   curl -X GET http://localhost:8080/api/users/all \
     -H "Authorization: Bearer eyJhbGci..."
   ```

### Para aplicaciones web:
- Almacenar el token en `localStorage` o `sessionStorage`
- Incluir el token en el header `Authorization` en cada petición
- Eliminar el token al hacer logout

### Para aplicaciones Swing/Java:
- Guardar el token en memoria después de autenticarse
- Incluir el token en el header de cada HttpURLConnection
- Limpiar el token al cerrar sesión

---

## Ejecutar la API

```bash
# Opción 1: Con Maven + Jetty
cd /home/eddy-c/workspace/b00tc4mp-java-202508/staff/eduardo-criado/product/api
mvn jetty:run

# Opción 2: Con Tomcat (usando el script de deploy)
./deploy.sh
```

La API estará disponible en `http://localhost:8080/api`
