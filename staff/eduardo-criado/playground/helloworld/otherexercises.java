public class otherexercises {
    public static void main(String[] args) {
        System.out.println("Hola, soy Eduardo!");
        System.out.println("Hola,\nsoy Eduardo!");
        System.err.println("mensaje de error!!!!!!!");

        System.out.println("Arte ASCII - Cara feliz:");
        System.out.println("  ^   ^  ");
        System.out.println("    o    ");
        System.out.println("  \\___/  ");
        System.out.println();

        String name = "Eduardo";
        System.out.println("Mi nombre es: " + name);

        name = "Manu";
        System.out.println("Mi nombre es: " + name);

        name = "37";
        System.out.println("Mi nombre es: " + name);

        int age = 20;
        System.out.println("Mi edad es: " + age);

        // constantes se usa "final"

        final String EMAIL = "yosoyyo@example.com";
        // email = "yanosoyyo@example.com";
        System.out.println("Mi email es: " + EMAIL);


        // infiere q esta variable es de tipo string por la definicion inicial/primera instancia "estoesotroemail@example.com" (inferencia de tipos)
        var email = "estoesotroemail@example.com";
        System.out.println("Mi email es: " + email);

        var year = 2025;
        System.out.println("Estamos en el año: " + year);

        // datos primitivos

        boolean myBoolean = true;
        // myBoolean = false;
        System.out.println(myBoolean);

        double myDouble = 1.77;
        System.out.println(myDouble);

        // float, long, byte, short

        char myChar = 'Z';
        System.out.println("Mi caracter preferido es: " + myChar);

        String myString = "Q pasa chaval?" ;
        // String myString = new String("Q pasa chaval?") ;
        
        System.out.println(myString);

        //para saber el tipo de dato se usa .getClass() y se imprime con .getSimpleName()
        System.out.println(myString.getClass().getSimpleName());

        System.out.println(((Object) myChar).getClass().getSimpleName());
        
        System.out.println(((Object) myDouble).getClass().getSimpleName());
        
        System.out.println(myString instanceof String);

    }
}