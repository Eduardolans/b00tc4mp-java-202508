package pruebas3;

import java.util.ArrayList;
import java.util.Arrays;

public class Functions {

    public static void main(String[] args) {

        /*
         * Clase 51 - Funciones
         * Vídeo: https://youtu.be/JOAqpdM36wI?t=186100
         */

        // Funciones

        for (int index = 0; index < 5; index++) {
            sendEmail();
        }

        // ...

        sendEmail();

        sendEmailToUser("edamos@gmail.com");
        sendEmailToUser("edulo@gmail.com", "EDU");

        var users = new ArrayList<>(Arrays.asList("edu@gmail.com", "marta@gmail.com"));
        sendEmailToUser(users);

        var state = sendEmailWithState("hola@caracola.com");
        System.out.println(state);

        System.out.println(sendEmailWithState(""));

        // sendEmailWithState("");

        // var state = sendEmailWithState();
        // System.out.println(sendEmailWithState("hola@caracola.com"));
        // System.out.println(state);
        // System.out.println(sendEmailWithState());
        // System.out.println(sendEmailWithState(""));

    }

    /*
     * Clase 52 - Funciones sin parámetros ni retorno
     * Vídeo: https://youtu.be/JOAqpdM36wI?t=18394
     */

    // Función sin parámetros ni retorno

    public static void sendEmail() {
        System.out.println("Se envía el email");
    }

    /*
     * Clase 53 - Funciones con parámetros / Sobrecarga
     * Vídeo: https://youtu.be/JOAqpdM36wI?t=18827
     */

    // Función con parámetros

    public static void sendEmailToUser(String email) {
        System.out.println("Se envía el email a " + email);
    }

    // Sobrecarga de funciones

    public static void sendEmailToUser(String email, String name) {
        System.out.println("Se envía el email a " + name + " (" + email + ")");
    }

    public static void sendEmailToUser(ArrayList<String> emails) {
        for (String email : emails) {
            // System.out.println("Se envía el email aaaaa " + email);
            sendEmailToUser(email);
        }
    }

    // public static void sendEmailToUser(ArrayList<String> emails) {
    // String correos = String.join(", ", emails);
    // System.out.println("Se envían los siguientes correos: " + correos);
    // }

    /*
     * Clase 54 - Funciones con retorno
     * Vídeo: https://youtu.be/JOAqpdM36wI?t=19027
     */

    // Función con retorno

    public static boolean sendEmailWithState(String email) {

        if (email.isEmpty()) {
            return false;
        }

        System.out.println("Se envía el email a " + email);
        return true;
    }
    // public static boolean sendEmailWithState() {

    // // if (email.isEmpty()) {
    // // return false;
    // // }

    // System.out.println("Se envía el email a " );
    // return true;
    // }
}
