package pruebas1;

public class DatabaseConnection {
    // Variable estática que almacena la única instancia
    private static DatabaseConnection instancia;

    // Método estático para obtener la instancia
    public static DatabaseConnection getInstancia() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    // Constructor privado - no se puede instanciar desde fuera
    private DatabaseConnection() {
        System.out.println("Conexión a base de datos creada");
    }

    public void conectar() {
        System.out.println("Conectando a la base de datos...");
    }
}

// Uso:
class Main {
    public static void main(String[] args) {
        // No se puede hacer: new DatabaseConnection(); // Error de compilación

        DatabaseConnection db1 = DatabaseConnection.getInstancia();
        DatabaseConnection db2 = DatabaseConnection.getInstancia();

        System.out.println(db1 == db2); // true - es el mismo objeto

        db1.conectar();
        db2.conectar();
    }
}
