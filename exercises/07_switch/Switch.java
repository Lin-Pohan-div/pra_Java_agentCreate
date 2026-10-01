public class Switch {

    public static String dayName(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 7
                return "Sunday";
            default:
                return "Unknown";
        }
    }

    public static boolean isVowel(char c) {
        switch (c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return true;
        }
    }

    public static String describe(int n) {
        // TODO: 0 回 "zero"、1 回 "one"、2 回 "two"，其他回 "many"
        switch (n) {
            case 0:
                return "zero";
            case 1:
                return "one";
            default:
                return "many";
        }
    }
}
