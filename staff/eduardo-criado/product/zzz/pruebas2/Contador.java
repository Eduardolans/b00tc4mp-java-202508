package pruebas2;

public class Contador {

    // Variable estática - compartida por todas las instancias
    private static int contadorGlobal = 0;

    public static void main(String[] args) {
        // Acceso a método estático sin crear objeto
        System.out.println("Contador global inicial: " +
                Contador.getContadorGlobal()); // 0

        Contador c1 = new Contador();
        Contador c2 = new Contador();

        System.out.println("Contador global: " + Contador.getContadorGlobal()); // 2
        System.out.println("c1 individual: " + c1.getContadorIndividual()); // 1
        System.out.println("c2 individual: " + c2.getContadorIndividual()); // 1

        c1.incrementarIndividual();
        System.out.println("c1 después de incrementar: " +
                c1.getContadorIndividual()); // 2
        System.out.println("c2 sin cambios: " + c2.getContadorIndividual()); // 1
    }

    // Método estático - se puede llamar sin crear objeto
    public static int getContadorGlobal() {
        return contadorGlobal;
    }

    // Variable no estática - cada objeto tiene su propia copia
    private int contadorIndividual = 0;

    // Constructor
    public Contador() {
        contadorGlobal++; // Incrementa el contador global
        contadorIndividual = 1; // Cada objeto inicia en 1
    }

    // Método no estático - requiere una instancia
    public int getContadorIndividual() {
        return contadorIndividual;
    }

    public void incrementarIndividual() {
        contadorIndividual++;
    }
}

class Main {
    public static void main(String[] args) {
        // Acceso a método estático sin crear objeto
        System.out.println("Contador global inicial: " +
                Contador.getContadorGlobal()); // 0

        Contador c1 = new Contador();
        Contador c2 = new Contador();

        System.out.println("Contador global: " + Contador.getContadorGlobal()); // 2
        System.out.println("c1 individual: " + c1.getContadorIndividual()); // 1
        System.out.println("c2 individual: " + c2.getContadorIndividual()); // 1

        c1.incrementarIndividual();
        System.out.println("c1 después de incrementar: " +
                c1.getContadorIndividual()); // 2
        System.out.println("c2 sin cambios: " + c2.getContadorIndividual()); // 1
    }
}