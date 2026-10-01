import dev.javaling.testkit.TestKit;

public class RecursionTest {

    public static void main(String[] args) {
        TestKit.expectEquals("factorial(5)", 120, Recursion.factorial(5));
        TestKit.expectEquals("factorial(0)", 1, Recursion.factorial(0));

        TestKit.expectEquals("fib(10)", 55, Recursion.fib(10));
        TestKit.expectEquals("fib(1)", 1, Recursion.fib(1));

        TestKit.expectEquals("sumDigits(1234)", 10, Recursion.sumDigits(1234));
        TestKit.expectEquals("sumDigits(9)", 9, Recursion.sumDigits(9));

        TestKit.expectEquals("gcd(48, 18)", 6, Recursion.gcd(48, 18));
        TestKit.expectEquals("gcd(7, 0)", 7, Recursion.gcd(7, 0));
        TestKit.done();
    }
}
