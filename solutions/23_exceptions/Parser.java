import java.util.regex.Pattern;

public class Parser {

    private static final Pattern NUMBER = Pattern.compile("-?\\d+");

    public static int parse(String text) {
        if (!NUMBER.matcher(text).matches()) {
            throw new IllegalArgumentException("不是數字: " + text);
        }
        return Integer.parseInt(text);
    }

    public static int parseOrDefault(String text, int fallback) {
        try {
            return parse(text);
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }
}
