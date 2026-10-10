// 先讀同資料夾的 README.md，再動手修改這個檔案。

public class Recursion {

    public static int factorial(int n) {
        // TODO: 回傳 n!，factorial(5) 應該是 120，factorial(0) 應該是 1
        if (n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    public static int fib(int n) {
        // TODO: 回傳第 n 個費氏數（fib(0)=0、fib(1)=1），fib(10) 應該是 55
        if (n < 2) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static int sumDigits(int n) {
        // TODO: 回傳十進位各位數字的和，sumDigits(1234) 應該是 10
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
