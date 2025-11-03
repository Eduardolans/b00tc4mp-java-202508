import assertion.Assertions;
import static assertion.Assertions.assertTrue;

public class AssertionDemo {
    public static void main(String[] args) {
        int value = 5;

        // Assertions.assertTrue(value > 0, "Value must be positive");
        assertTrue(value > 0, "Value must be positive");
        Assertions.assertEquals("Hello", "Hello", "Strings should be equal");
        System.out.println("All assertions passed.");

        // Demonstrate assertDoesNotThrow

        Assertions.assertDoesNotThrow(() -> {
            // Code that should not throw an exception
            int result = value / 1;
        }, "Division by non-zero should not throw");    

        System.out.println("No exception thrown as expected.");
        
        // Demonstrate assertThrows

        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> {
            // Code that should throw an exception
            int result = value / 0;
        }, "Division by zero should throw ArithmeticException");
        
        System.out.println("Caught expected exception: " + exception);  
    }
}