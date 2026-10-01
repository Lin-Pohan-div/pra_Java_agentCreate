public class Overloads {

    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static String describe(int n) {
        return "int " + n;
    }

    public static String describe(String s) {
        return s;
    }

    public static int sum(int... values) {
        int total = 0;
        for (int v : values) {
            total += v;
        }
        return total;
    }
}
