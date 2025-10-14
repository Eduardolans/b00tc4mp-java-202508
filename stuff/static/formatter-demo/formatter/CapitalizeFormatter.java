package formatter;

import formatter.IFormatter;

public class CapitalizeFormatter implements IFormatter {
    @Override
    public String format(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }
}