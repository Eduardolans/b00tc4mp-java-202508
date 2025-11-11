# API de Gestión de Usuarios

API RESTful para gestión de usuarios con autenticación JWT (JSON Web Tokens).

## Características

- ✅ Registro de usuarios
- ✅ Autenticación con JWT
- ✅ Endpoints protegidos con tokens
- ✅ Obtener información de usuarios
- ✅ Listar todos los usuarios
- ✅ Tokens con expiración de 24 horas

## Tecnologías

- **Java Servlets** (javax.servlet 4.0.1)
- **JWT** (jjwt 0.11.5)
- **JSON** (org.json)
- **JUnit 5** (5.9.3) para tests unitarios
- **Maven** para gestión de dependencias
- **Jetty** o **Tomcat** como servidor

## Estructura del Proyecto

```
api/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── api/              # Servlets (controladores)
│   │   │   │   ├── RegisterUserServlet.java
│   │   │   │   ├── AuthenticateUserServlet.java
│   │   │   │   ├── GetAllUsersServlet.java
│   │   │   │   ├── GetUserInfoServlet.java
│   │   │   │   ├── ResetDataServlet.java   # Solo para testing
│   │   │   │   └── HelloServlet.java
│   │   │   ├── api/util/         # Utilidades
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── JwtHelper.java
│   │   │   ├── logic/            # Lógica de negocio
│   │   │   │   └── Logic.java
│   │   │   ├── data/             # Acceso a datos
│   │   │   │   ├── Data.java
│   │   │   │   └── User.java
│   │   │   └── errors/           # Excepciones personalizadas
│   │   │       ├── CredentialsException.java
│   │   │       ├── DuplicityException.java
│   │   │       └── NotFoundException.java
│   │   └── webapp/WEB-INF/
│   │       └── web.xml
│   └── test/
│       ├── java/com/example/     # Tests unitarios (JUnit 5)
│       │   ├── data/DataTest.java       (13 tests)
│       │   └── logic/LogicTest.java     (15 tests)
│       └── bash/                 # Tests de integración (curl)
│           ├── register-user.sh
│           ├── authenticate-user.sh
│           ├── get-all-users.sh
│           ├── get-user-info.sh
│           ├── reset-data.sh
│           └── hello.sh
├── pom.xml
├── deploy.sh
├── API_ENDPOINTS.md              # Documentación detallada de endpoints
└── README.md
```

## Cómo Ejecutar

### Opción 1: Con Maven + Jetty (Desarrollo)

```bash
cd /home/eddy-c/workspace/b00tc4mp-java-202508/staff/eduardo-criado/product/api
mvn jetty:run
```

La API estará disponible en `http://localhost:8080/api`

### Opción 2: Con Tomcat (Producción)

```bash
cd /home/eddy-c/workspace/b00tc4mp-java-202508/staff/eduardo-criado/product/api
./deploy.sh
```

## Endpoints Disponibles

### Públicos (no requieren autenticación):
- `POST /users` - Registrar usuario
- `POST /users/auth` - Autenticar y obtener token JWT
- `POST /test/reset` - Resetear datos (⚠️ solo para testing)
- `GET /hello` - Endpoint de prueba

### Protegidos (requieren JWT):
- `GET /users/all` - Obtener todos los usuarios
- `GET /users/info` - Obtener información del usuario autenticado

## Uso Rápido

### 1. Registrar un usuario

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan Pérez","username":"juanperez","password":"pass123"}'
```

### 2. Autenticarse y obtener token

```bash
curl -X POST http://localhost:8080/api/users/auth \
  -H "Content-Type: application/json" \
  -d '{"username":"juanperez","password":"pass123"}'
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Nota:** El cliente debe agregar el prefijo `Bearer ` al token cuando lo use en peticiones.

### 3. Usar el token en peticiones protegidas

```bash
# Obtener info del usuario autenticado
curl -X GET http://localhost:8080/api/users/info \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."

# Obtener todos los usuarios
curl -X GET http://localhost:8080/api/users/all \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

## Tests

### Tests Unitarios (JUnit 5)

Ejecutar tests unitarios que prueban la lógica de negocio de forma aislada:

```bash
cd api
mvn clean test
```

**Resultado:**
```
[INFO] Running com.example.data.DataTest
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.example.logic.LogicTest
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0

Total: 28 tests
```

**Características:**
- ✅ No requieren servidor corriendo
- ✅ Muy rápidos (~70ms)
- ✅ Prueban Logic y Data directamente
- ✅ Usan reflection para resetear singletons

### Tests de Integración (Bash/curl)

Ejecutar tests de integración que prueban la API completa end-to-end:

⚠️ **Requiere que la API esté corriendo** (`mvn jetty:run`)

```bash
# Resetear datos antes de empezar
./src/test/bash/reset-data.sh

# Registrar usuarios de prueba
./src/test/bash/register-user.sh

# Autenticar
./src/test/bash/authenticate-user.sh

# Obtener todos los usuarios (con JWT)
./src/test/bash/get-all-users.sh

# Obtener info de usuario (con JWT)
./src/test/bash/get-user-info.sh

# Test simple
./src/test/bash/hello.sh
```

### Diferencias entre Tests

| Tipo | Unitarios | Integración |
|------|-----------|-------------|
| **Servidor** | ❌ No requiere | ✅ Requiere corriendo |
| **Velocidad** | ⚡ Muy rápido | 🐌 Más lento |
| **HTTP** | ❌ No usa | ✅ Usa HTTP real |
| **Alcance** | Lógica aislada | Sistema completo |

Ver [TESTS_API_APP.md](../TESTS_API_APP.md) para más detalles sobre las diferencias.

## Autenticación JWT

La API usa JSON Web Tokens para autenticación stateless:

1. El cliente se autentica con username/password
2. El servidor genera un token JWT válido por 24 horas
3. El cliente guarda el token (localStorage, memoria, etc.)
4. El cliente incluye el token en el header `Authorization: Bearer <token>` en cada petición
5. El servidor valida el token en endpoints protegidos

### Ventajas:
- Sin estado (stateless)
- Escalable
- Seguro
- Compatible con aplicaciones web y móviles

## Documentación Completa

Ver [API_ENDPOINTS.md](API_ENDPOINTS.md) para documentación detallada de todos los endpoints, códigos de respuesta y ejemplos.

## Compilar

```bash
mvn clean package
```

Genera `target/api.war` listo para desplegar.

## Autor

Eduardo Criado
