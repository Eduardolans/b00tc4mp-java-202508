package formatter;

public class UpperCaseFormatter implements IFormatter {
    @Override
    public String format(String input) {
        return input.toUpperCase();
    }
}