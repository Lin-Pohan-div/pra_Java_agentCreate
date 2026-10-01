public class Switch {

    public static String dayName(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 7:
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
                return false;
        }
    }

    public static String describe(int n) {
        switch (n) {
            case 0:
                return "zero";
            case 1:
                return "one";
            case 2:
                return "two";
            default:
                return "many";
        }
    }
}
