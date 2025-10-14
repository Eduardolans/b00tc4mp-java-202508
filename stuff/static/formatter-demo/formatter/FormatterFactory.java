package formatter;


public class FormatterFactory {
    public static IFormatter getFormatter(String type) {
        switch (type.toLowerCase()) {
            case "upper":
                return new UpperCaseFormatter();
            case "lower":
                return new LowerCaseFormatter();
            case "capitalize":
                return new CapitalizeFormatter();
            default:
                throw new IllegalArgumentException("Unknown formatter type: " + type);
        }
    }

    public static IFormatter getUpperCaseFormatter() {
        return FormatterFactory.getFormatter("upper");
    }

    public static IFormatter getLowerCaseFormatter() {
        return FormatterFactory.getFormatter("lower");
    }

    public static IFormatter getCapitalizeFormatter() {
        return FormatterFactory.getFormatter("capitalize");
    }
    private FormatterFactory() {
        // Private constructor to prevent instantiation
    }   
}