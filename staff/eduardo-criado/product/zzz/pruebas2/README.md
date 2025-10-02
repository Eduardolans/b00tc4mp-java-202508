## Estatico vs No Estatico (Static vs Non-Static)

Static (estático) significa que un miembro (variable o método) pertenece a la clase en lugar de a las instancias individuales.

Non-static (no estático) significa que pertenece a cada objeto creado de la clase.

### Elementos Estaticos (static):

Área de Clase (Static)

- Pertenecen a la clase, no a instancias específicas

- Se cargan en memoria cuando la clase se carga por primera vez

- Se comparten entre todas las instancias de la clase

- Se pueden acceder sin crear objetos de la clase

### Elementos No Estaticos:

Área de Instancia (No-Static)

- Pertenecen a cada instancia individual de la clase

- Cada objeto tiene su propia copia
  Requieren crear un objeto para ser accedidos

## Resumen

Visualización de la Memoria

Imagina que la memoria se divide en dos áreas:

### Area de Clase (Static) - Variables y Métodos:

- Una sola copia para toda la clase (No importa cuántos objetos crees)

- Compartida por todos los objetos (Todos los objetos ven el mismo valor)

- Pertenecen a la clase - No a objetos individuales

- Existe aunque no hayas creado ningun objeto

### Area de Instancia (No-Static) - Variables y Métodos::

- Una copia por cada objeto creado (Cada objeto tiene la suya)

- Independiente entre objetos (Pertenecen a cada instancia -> son independientes)

- Requieren un objeto para usarse

- Solo existe cuando creas un objeto

- Valores independientes (Cambiar uno no afecta otros)

### Analogía con la vida real:

🏛️ STATIC es como el "contador de visitantes" de un museo: - Solo hay UN contador para todo el museo - Cada persona que entra incrementa EL MISMO contador - Puedes preguntar el total sin ser un visitante 🏠 NO-STATIC es como el "numero de habitacion" de cada persona: - Cada huesped tiene SU PROPIO numero - Cambiar tu numero no afecta el de otros - Necesitas ser un huésped para tener un numero"

```public class Ejemplo {
    // Variable static - compartida por todas las instancias
    static int contadorGlobal = 0;

    // Variable non-static - única para cada instancia
    int contadorLocal = 0;

    // Método static - se puede llamar sin crear un objeto
    static void metodoEstatico() {
        System.out.println("Soy estático, no necesito un objeto");
        // Solo puede acceder a miembros static
        contadorGlobal++;
    }

    // Método non-static - requiere una instancia
    void metodoDeInstancia() {
        System.out.println("Necesito un objeto para ejecutarme");
        contadorLocal++;
        contadorGlobal++; // Puede acceder a static también
    }
}

// Uso:
Ejemplo.metodoEstatico(); // Llamada directa a la clase

Ejemplo obj1 = new Ejemplo();
Ejemplo obj2 = new Ejemplo();
obj1.metodoDeInstancia(); // Llamada a través del objeto
```

### Diferencias clave:

- Static: Un valor compartido entre todos los objetos. Se accede con NombreClase.miembro

- Non-static: Cada objeto tiene su propia copia. Se accede con objeto.miembro
