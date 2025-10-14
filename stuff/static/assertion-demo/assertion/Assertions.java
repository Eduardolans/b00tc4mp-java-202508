package assertion;

public class Assertions {
    public static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) {
            return;
        }

        if (expected != null && expected.equals(actual)) {
            return;
        }

        throw new AssertionError("Assertion failed: " + message + ". Expected: " + expected + ", but got: " + actual);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    public static void assertDoesNotThrow(Runnable runnable, String message) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new AssertionError("Assertion failed: " + message + ". Expected no exception, but got: " + e);
        }
    }

    public static Exception assertThrows(Class<? extends Exception> expectedException, Runnable runnable, String message) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {
                return e;
            } else {
                throw new AssertionError("Assertion failed: " + message + ". Expected exception of type: " + expectedException.getName() + ", but got: " + e);
            }
        }
        throw new AssertionError("Assertion failed: " + message + ". Expected exception of type: " + expectedException.getName() + ", but no exception was thrown.");
    }
}