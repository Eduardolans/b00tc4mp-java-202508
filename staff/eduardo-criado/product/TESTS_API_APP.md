# Guía de Tests: API vs App

Este documento explica las diferencias entre los tests unitarios de la API y los tests de integración de la App.

---

## Índice

- [Diferencias Principales](#diferencias-principales)
- [Tests de la API (Unitarios)](#tests-de-la-api-unitarios)
- [Tests de la App (Integración)](#tests-de-la-app-integración)
- [Comparación Visual](#comparación-visual)
- [Cuándo Usar Cada Tipo](#cuándo-usar-cada-tipo)
- [Ejecutar Tests](#ejecutar-tests)

---

## Diferencias Principales

| Aspecto | Tests API (Unitarios) | Tests App (Integración) |
|---------|----------------------|-------------------------|
| **Ubicación** | `api/src/test/java/` | `app/src/test/java/` |
| **Tipo** | Unit tests | Integration tests |
| **Alcance** | Prueba clases individuales aisladas | Prueba comunicación cliente-servidor completa |
| **Llamadas HTTP** | ❌ NO | ✅ SÍ |
| **Servidor requerido** | ❌ NO necesita | ✅ SÍ necesita (API corriendo) |
| **Velocidad** | ⚡ Muy rápido (~70ms para 28 tests) | 🐌 Más lento (latencia de red) |
| **Dependencias** | Solo código Java | HTTP, JSON, red, servidor |
| **Fallo típico** | Lógica de negocio incorrecta | API no corriendo, red, formato JSON |

---

## Tests de la API (Unitarios)

### Ubicación
```
api/src/test/java/
├── com/example/data/DataTest.java      (13 tests)
└── com/example/logic/LogicTest.java    (15 tests)
```

### Características

✅ **Prueban clases individuales de forma aislada**
- Llaman directamente a métodos de `Logic` y `Data`
- No hacen llamadas HTTP
- No necesitan servlets ni servidor

✅ **Muy rápidos**
```
Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
Time elapsed: ~70ms
```

✅ **No requieren infraestructura**
- No necesitas levantar el servidor
- No hay latencia de red
- Feedback inmediato

### Ejemplo de Test Unitario

```java
@Test
@DisplayName("Should register a user successfully")
void testRegisterUser_Success() throws DuplicityException {
    // Act - Llama DIRECTAMENTE al método de Logic
    logic.registerUser("Juan Pérez", "juanperez", "pass123");

    // Assert - Verifica DIRECTAMENTE en Data (en memoria)
    Data data = Data.get();
    User user = data.findUserByUsername("juanperez");

    assertNotNull(user, "User should exist in data");
    assertEquals("Juan Pérez", user.getName());
    assertEquals("juanperez", user.getUsername());
}
```

**Flujo:**
```
Test → Logic.registerUser() → Data.addUser() → ✓
       (todo en memoria, sin HTTP)
```

### Ejecutar Tests Unitarios

```bash
cd api
mvn clean test

# Resultado:
# [INFO] Running com.example.data.DataTest
# [INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
# [INFO] Running com.example.logic.LogicTest
# [INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
```

---

## Tests de la App (Integración)

### Ubicación
```
app/src/test/java/
└── logic/LogicTest.java    (6 tests de integración)
```

### Características

✅ **Prueban la comunicación cliente-servidor completa**
- Hacen llamadas HTTP reales usando `HttpURLConnection`
- Requieren que la API esté corriendo en `http://localhost:8080/api`
- Prueban el flujo end-to-end completo

✅ **Más lentos pero más realistas**
- Incluyen latencia de red
- Prueban serialización/deserialización JSON
- Verifican headers HTTP, códigos de estado

⚠️ **REQUIEREN que la API esté corriendo**
```bash
# Si la API no está corriendo:
java.net.ConnectException: Connection refused
```

### Ejemplo de Test de Integración

```java
@Test
@DisplayName("Should register a user successfully")
void testRegisterUser_Success() throws Exception {
    // Act - Hace una llamada HTTP REAL a la API
    logic.registerUser("Juan Pérez", "juanperez", "pass123");

    // Internamente esto hace:
    // 1. Crea JSON: {"name":"Juan Pérez","username":"juanperez","password":"pass123"}
    // 2. Abre HttpURLConnection a http://localhost:8080/api/users
    // 3. Envía POST con el JSON
    // 4. El servlet recibe y procesa
    // 5. Devuelve HTTP 201 Created

    // Assert - Si no lanza excepción, fue exitoso
}
```

**Flujo:**
```
Test → App Logic
         ↓ HTTP POST (JSON)
       Servlet → API Logic → API Data
         ↓ HTTP 201 Created
       App Logic → ✓
```

### Ejecutar Tests de Integración

```bash
# Paso 1: Levantar la API (en terminal 1)
cd api
mvn jetty:run

# Paso 2: Ejecutar tests de la app (en terminal 2)
cd app
mvn clean test

# Resultado:
# [INFO] Running logic.LogicTest
# [INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

---

## Comparación Visual

### Tests Unitarios (API)
```
┌─────────────────┐
│   LogicTest     │
│   DataTest      │
└────────┬────────┘
         │ Llamada directa (en memoria)
         ↓
    ┌────────┐      ┌──────┐
    │ Logic  │ ---> │ Data │
    └────────┘      └──────┘

NO hay HTTP, NO hay servlets, NO hay red
```

### Tests de Integración (App)
```
┌─────────────────┐
│ App LogicTest   │
└────────┬────────┘
         │ HTTP POST/GET
         │ (JSON, headers, códigos de estado)
         ↓
    ┌────────────┐
    │  Servlet   │ (API corriendo en Jetty/Tomcat)
    └─────┬──────┘
          │
          ↓
    ┌─────────┐      ┌──────────┐
    │API Logic│ ---> │ API Data │
    └─────────┘      └──────────┘

SÍ hay HTTP, SÍ hay servlets, SÍ hay red
```

---

## Cuándo Usar Cada Tipo

### Tests Unitarios (API) 👍 Usar para:

✅ **Probar lógica de negocio aislada**
- Validaciones de datos
- Algoritmos y cálculos
- Manejo de excepciones personalizadas
- Operaciones CRUD en memoria

✅ **Desarrollo con feedback rápido**
- Ejecutar mientras desarrollas
- TDD (Test-Driven Development)
- Encontrar bugs rápidamente

✅ **Tests que no dependen de infraestructura**
- No necesitas servidor
- No necesitas base de datos
- No necesitas red

**Ejemplo:** ¿El método `registerUser` lanza `DuplicityException` cuando el username ya existe?

### Tests de Integración (App) 👍 Usar para:

✅ **Verificar comunicación cliente-servidor**
- ¿El JSON se serializa correctamente?
- ¿Los headers HTTP son correctos?
- ¿Los códigos de estado son apropiados?

✅ **Probar el sistema completo funcionando junto**
- Flujo end-to-end: registro → login → consultas
- Integración de todos los componentes
- Comportamiento real del sistema

✅ **Tests de aceptación**
- ¿El sistema funciona como se espera desde el punto de vista del usuario?
- ¿La API REST cumple con el contrato definido?

**Ejemplo:** ¿Un cliente puede registrarse, autenticarse y obtener sus datos correctamente?

---

## Ejecutar Tests

### Ejecutar Solo Tests Unitarios (API)

```bash
cd api
mvn clean test

# Ventaja: No necesitas levantar servidor
# Tiempo: ~1-2 segundos
```

### Ejecutar Solo Tests de Integración (App)

```bash
# Terminal 1: Levantar API
cd api
mvn jetty:run

# Terminal 2: Ejecutar tests
cd app
mvn clean test

# Ventaja: Prueba el sistema real
# Desventaja: Más lento, requiere API corriendo
```

### Ejecutar Todos los Tests (Workflow Completo)

```bash
# 1. Resetear datos de la API
cd api
./src/test/bash/reset-data.sh

# 2. Levantar la API (dejar corriendo)
mvn jetty:run

# En otra terminal:

# 3. Ejecutar tests unitarios de la API
cd api
mvn test

# 4. Ejecutar tests de integración de la app
cd ../app
mvn test
```

---

## Resumen

| Pregunta | Tests Unitarios (API) | Tests Integración (App) |
|----------|----------------------|-------------------------|
| ¿Qué prueba? | Lógica de negocio individual | Sistema completo end-to-end |
| ¿Cuándo falla? | Error en la lógica | Error en comunicación HTTP/JSON |
| ¿Qué tan rápido? | ⚡ Muy rápido | 🐌 Más lento |
| ¿Necesita servidor? | ❌ No | ✅ Sí |
| ¿Usa HTTP? | ❌ No | ✅ Sí |
| ¿Para desarrollo? | ✅ Ideal | ⚠️ Menos frecuente |
| ¿Para CI/CD? | ✅ Siempre | ✅ También |
| ¿Detecta bugs en...? | Lógica | Integración/comunicación |

---

## Ver También

- [API_ENDPOINTS.md](api/API_ENDPOINTS.md) - Documentación completa de todos los endpoints de la API
- [API README.md](api/README.md) - Información general del proyecto API
- [App README.md](app/README.md) - Información general del proyecto App
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/) - Guía oficial de JUnit 5

---

## Notas Finales

💡 **Consejo:** Escribe AMBOS tipos de tests:
- Los tests unitarios te dan feedback rápido durante el desarrollo
- Los tests de integración te aseguran que todo funciona junto en producción

🎯 **Regla general:**
- 70% tests unitarios (rápidos, muchos, bajo nivel)
- 30% tests de integración (lentos, menos, alto nivel)
