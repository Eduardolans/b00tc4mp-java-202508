import formatter.IFormatter;
import formatter.FormatterFactory;

public class FormatterDemo {
    public static void main(String[] args) {
        // IFormater upperFormatter = FormatterFactory.getFormatter("upper");
        IFormatter upperFormatter = FormatterFactory.getUpperCaseFormatter();
        IFormatter lowerFormatter = FormatterFactory.getFormatter("lower");
        IFormatter capitalizeFormatter = FormatterFactory.getFormatter("capitalize");

        String text = "Hello, World!";
        System.out.println(upperFormatter.format(text));
        System.out.println(lowerFormatter.format(text));
        System.out.println(capitalizeFormatter.format(text));
    }
}