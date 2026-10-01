public class Operators {

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }
}
