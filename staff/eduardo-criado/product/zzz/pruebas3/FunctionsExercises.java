package pruebas3;

import java.util.ArrayList;

/*
Clase 55 - Ejercicios: Funciones
Vídeo: https://youtu.be/JOAqpdM36wI?t=19521
*/
public class FunctionsExercises {
    public static void main(String[] args) {
        // 1. Crea una función que imprima "¡Te doy la bienvenida al curso de Java desde
        // cero!".
        System.out.println("--- Ejercicio 1 ---");
        darBienvenida();

        // 2. Escribe una función que reciba un nombre como parámetro y salude a esa
        // persona.
        System.out.println("\n--- Ejercicio 2 ---");
        saludar("Carlos");
        saludar("María");

        // 3. Haz un método que reciba dos números enteros y devuelva su resta.
        System.out.println("\n--- Ejercicio 3 ---");
        int resultado = restar(10, 4);
        System.out.println("10 - 4 = " + resultado);

        // 4. Crea un método que calcule el cuadrado de un número (n * n).
        System.out.println("\n--- Ejercicio 4 ---");
        int cuadrado = calcularCuadrado(5);
        System.out.println("El cuadrado de 5 es: " + cuadrado);

        // 5. Escribe una función que reciba un número y diga si es par o impar.
        System.out.println("\n--- Ejercicio 5 ---");
        verificarParImpar(8);
        verificarParImpar(7);

        // 6. Crea un método que reciba una edad y retorne true si es mayor de edad (y
        // false en caso contrario).
        System.out.println("\n--- Ejercicio 6 ---");
        boolean esMayor = esMayorDeEdad(20);
        System.out.println("¿Es mayor de edad (20 años)? " + esMayor);
        esMayor = esMayorDeEdad(15);
        System.out.println("¿Es mayor de edad (15 años)? " + esMayor);

        // 7. Implementa una función que reciba una cadena y retorne su longitud.
        System.out.println("\n--- Ejercicio 7 ---");
        int longitud = obtenerLongitud("Hola Mundo");
        System.out.println("La longitud de 'Hola Mundo' es: " + longitud);

        // 8. Crea un método que reciba un array de enteros, calcula su media y lo
        // retorna.
        System.out.println("\n--- Ejercicio 8 ---");
        int[] numeros = { 5, 10, 15, 20, 25 };
        double media = calcularMedia(numeros);
        System.out.println("La media del array es: " + media);

        // 9. Escribe un método que reciba un número y retorna su factorial.
        System.out.println("\n--- Ejercicio 9 ---");
        int factorial = calcularFactorial(-5);
        System.out.println("El factorial de 5 es: " + factorial);

        // 10. Crea una función que reciba un ArrayList<String> y lo recorra mostrando
        // cada elemento.
        System.out.println("\n--- Ejercicio 10 ---");
        ArrayList<String> lenguajes = new ArrayList<>();
        lenguajes.add("Java");
        lenguajes.add("Python");
        lenguajes.add("JavaScript");
        lenguajes.add("C++");
        recorrerLista(lenguajes);
    }

    // Ejercicio 1: Función que imprime un mensaje de bienvenida
    public static void darBienvenida() {
        System.out.println("¡Te doy la bienvenida al curso de Java desde cero!");
    }

    // Ejercicio 2: Función que recibe un nombre y lo saluda
    public static void saludar(String nombre) {
        System.out.println("¡Hola, " + nombre + "! ¿Cómo estás?");
    }

    // Ejercicio 3: Función que resta dos números
    public static int restar(int a, int b) {
        return a - b;
    }

    // Ejercicio 4: Función que calcula el cuadrado de un número
    public static int calcularCuadrado(int n) {
        return n * n;
    }

    // Ejercicio 5: Función que verifica si un número es par o impar
    public static void verificarParImpar(int numero) {
        if (numero % 2 == 0) {
            System.out.println(numero + " es PAR");
        } else {
            System.out.println(numero + " es IMPAR");
        }
    }

    // Ejercicio 6: Función que verifica si es mayor de edad (>= 18)
    public static boolean esMayorDeEdad(int edad) {
        return edad >= 18;
    }

    // Ejercicio 7: Función que retorna la longitud de una cadena
    public static int obtenerLongitud(String texto) {
        return texto.length();
    }

    // Ejercicio 8: Función que calcula la media de un array
    public static double calcularMedia(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int suma = 0;
        for (int num : array) {
            suma += num;
        }

        return (double) suma / array.length;
    }

    // Ejercicio 9: Función que calcula el factorial de un número
    public static int calcularFactorial(int n) {
        if (n < 0) {
            System.out.println("No se puede calcular factorial de números negativos");
            return -1;
        }

        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }

        return factorial;
    }

    // Ejercicio 10: Función que recorre y muestra un ArrayList de Strings
    public static void recorrerLista(ArrayList<String> lista) {
        System.out.println("Elementos de la lista:");
        for (String elemento : lista) {
            System.out.println("- " + elemento);
        }
    }
}
