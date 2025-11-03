# Documentación de la API - Endpoints Disponibles

## Base URL
```
http://localhost:8080/api
```

---

## 1. Registro de Usuario

### `POST /register`

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

**201 Created** - Usuario registrado exitosamente
```json
{
  "success": true,
  "message": "User registered successfully"
}
```

**409 Conflict** - Usuario ya existe
```json
{
  "success": false,
  "error": "DuplicityException",
  "message": "user already exists"
}
```

**Ejemplo curl:**
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","username":"juanperez","password":"pass123"}'
```

---

## 2. Login

### `POST /login`

Inicia sesión y crea una sesión HTTP.

**Request Body:**
```json
{
  "username": "juanperez",
  "password": "miPassword123"
}
```

**Respuestas:**

**200 OK** - Login exitoso
```json
{
  "success": true,
  "message": "Login successful",
  "name": "Juan Pérez",
  "username": "juanperez"
}
```

**401 Unauthorized** - Credenciales inválidas
```json
{
  "success": false,
  "error": "CredentialsException",
  "message": "invalid credentials"
}
```

**Ejemplo curl:**
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{"username":"juanperez","password":"pass123"}'
```

**Nota:** Usa `-c cookies.txt` para guardar la sesión en un archivo.

---

## 3. Obtener Usuario

### `GET /user` o `GET /user?username=xxx`

Obtiene los datos de un usuario. Tiene dos modos de uso:

1. **Sin parámetros:** Devuelve el usuario actualmente logueado (requiere sesión activa)
2. **Con parámetro `username`:** Devuelve el usuario especificado (público)

**Parámetros de Query (opcionales):**
- `username` - Username del usuario a consultar

**Respuestas:**

**200 OK** - Datos del usuario
```json
{
  "success": true,
  "name": "Juan Pérez",
  "username": "juanperez"
}
```

**401 Unauthorized** - No hay sesión activa y no se proporcionó username
```json
{
  "success": false,
  "error": "Unauthorized",
  "message": "No active session and no username provided"
}
```

**404 Not Found** - Usuario no encontrado
```json
{
  "success": false,
  "error": "NotFoundException",
  "message": "user not found"
}
```

**Ejemplos curl:**
```bash
# Obtener usuario actual (requiere sesión)
curl -X GET http://localhost:8080/api/user \
  -b cookies.txt

# Obtener usuario específico (público)
curl -X GET "http://localhost:8080/api/user?username=juanperez"
```

---

## 4. Obtener Todos los Usuarios

### `GET /users`

Obtiene la lista de todos los usuarios registrados en el sistema.

**Respuestas:**

**200 OK** - Lista de usuarios
```json
{
  "success": true,
  "count": 2,
  "users": [
    {
      "name": "Juan Pérez",
      "username": "juanperez"
    },
    {
      "name": "María García",
      "username": "mariagarcia"
    }
  ]
}
```

**Nota:** Por seguridad, las contraseñas NO se incluyen en la respuesta.

**Ejemplo curl:**
```bash
curl -X GET http://localhost:8080/api/users
```

---

## 5. Logout

### `POST /logout`

Cierra la sesión actual.

**Respuestas:**

**200 OK** - Logout exitoso
```json
{
  "success": true,
  "message": "Logout successful"
}
```

**Ejemplo curl:**
```bash
curl -X POST http://localhost:8080/api/logout \
  -b cookies.txt
```

---

## Gestión de Sesiones

La API usa **sesiones HTTP** para mantener el estado del usuario logueado:

- Al hacer login exitoso, se crea una sesión en el servidor
- La sesión almacena el `username` del usuario logueado
- Las cookies de sesión se envían automáticamente en las peticiones subsecuentes
- Al hacer logout, la sesión se invalida

### Usando sesiones desde una aplicación Java (Swing)

Desde una aplicación Java cliente (como tu App Swing), NO puedes usar las sesiones HTTP directamente porque cada petición HTTP se hace de forma independiente.

**Soluciones para el cliente Java:**
1. **Opción Simple (actual):** Guardar los datos del usuario en memoria local después del login
2. **Opción con tokens:** Implementar autenticación basada en tokens JWT
3. **Opción con cookies:** Usar `CookieManager` en Java para manejar las cookies de sesión

---

## Flujo de Trabajo Recomendado

### Para usar desde el navegador o herramientas (Postman, curl):
1. POST `/register` - Registrar usuario
2. POST `/login` - Iniciar sesión (guarda la cookie)
3. GET `/user` - Obtener datos del usuario actual (usa la cookie)
4. GET `/users` - Obtener todos los usuarios registrados
5. POST `/logout` - Cerrar sesión

### Para usar desde la App Swing:
1. POST `/register` - Registrar usuario
2. POST `/login` - Iniciar sesión y **guardar el nombre en memoria local**
3. Usar el nombre guardado localmente (no necesitas llamar a `/user`)
4. No necesitas `/logout` (opcional)

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
