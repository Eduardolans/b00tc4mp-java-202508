package formatter;

public class LowerCaseFormatter implements IFormatter {
    @Override
    public String format(String input) {
        return input.toLowerCase();
    }
}