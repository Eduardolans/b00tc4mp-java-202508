## Patrón Singleton

- El patrón Singleton garantiza que una clase tenga una sola instancia en toda la aplicación y proporciona un punto de acceso global a ella.

### Características:

- Constructor privado (impide crear instancias desde fuera)

- Método estático para obtener la instancia

- Variable estática que almacena la única instancia

---

### ¿Cuándo usar Singleton?

- Conexiones a bases de datos

- Gestores de configuración

- Loggers

- Caches globales

//Versión thread-safe (para aplicaciones multihilo):

```javapublic class Singleton {
    private static volatile Singleton instancia;

    private Singleton() {}

    public static Singleton obtenerInstancia() {
        if (instancia == null) {
            synchronized (Singleton.class) {
                if (instancia == null) {
                    instancia = new Singleton();
                }
            }
        }
        return instancia;
    }
}
```

## Modificadores de Acceso (Access Modifiers)

- Controlan la visibilidad y accesibilidad de clases, métodos y variables. Java tiene 4 niveles:

### public:

- Accesible desde cualquier parte del programa

- Sin restricciones de acceso

### private:

- Accesible solo dentro de la misma clase

- Máximo nivel de encapsulación

### protected:

- Accesible dentro del mismo paquete y por subclases (herencia)

- Intermedio entre private y public

### default (sin modificador):

- Accesible solo dentro del mismo paquete

- También llamado "package-private"

---

```
public class MiClase {
    // PRIVATE - solo accesible dentro de esta clase
    private String secreto = "Solo yo puedo verlo";

    // DEFAULT (sin modificador) - accesible en el mismo paquete
    int numeroPaquete = 10;

    // PROTECTED - accesible en el mismo paquete y subclases
    protected String datoProtegido = "Herencia permitida";

    // PUBLIC - accesible desde cualquier lugar
    public String datoPublico = "Todos pueden verme";

    private void metodoPrivado() {
        System.out.println("Solo llamable desde aquí");
    }

    public void metodoPublico() {
        System.out.println("Llamable desde cualquier lugar");
        metodoPrivado(); // OK, estamos en la misma clase
    }
}
```

#### Desde otra clase en otro paquete:

```
MiClase obj = new MiClase();
obj.datoPublico;        // ✅ OK
obj.secreto;            // ❌ ERROR: es private
obj.metodoPrivado();    // ❌ ERROR: es private
```

### Buenas prácticas:

- Usa private para variables por defecto (encapsulación)

- Usa public para métodos que forman la API de tu clase

- Usa protected cuando quieras permitir herencia pero no acceso público

- Evita el default a menos que tengas una razón específica

#### Ejemplo de encapsulación correcta:

```
javapublic class CuentaBancaria {
    private double saldo; // Protegido

    // Métodos públicos para acceder/modificar de forma controlada
    public double obtenerSaldo() {
        return saldo;
    }

    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
        }
    }
}
```
