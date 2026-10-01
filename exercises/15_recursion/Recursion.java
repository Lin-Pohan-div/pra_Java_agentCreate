public class Recursion {

    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    public static int fib(int n) {
        if (n < 2) {
            return n;
        }
        return fib(n - 1) + fib(n - 2)
    }

    public static int sumDigits(int n) {
        if (n == 0) {
            return 0;
        }
        return n % 10 + sumDigits(n / 10);
    }

    public static int gcd(int a, int b) {
        // TODO: 歐幾里得演算法，gcd(48, 18) 應該是 6
        return b == 0 ? a : gcd(b, a);
    }
}
