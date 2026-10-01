import dev.javaling.testkit.TestKit;

public class OperatorsTest {

    public static void main(String[] args) {
        TestKit.expectTrue("isEven(4)", Operators.isEven(4));
        TestKit.expectTrue("isEven(0)", Operators.isEven(0));
        TestKit.expectFalse("isEven(3)", Operators.isEven(3));
        TestKit.expectFalse("isEven(-3)", Operators.isEven(-3));

        TestKit.expectEquals("max(9, 4)", 9, Operators.max(9, 4));
        TestKit.expectEquals("max(4, 9)", 9, Operators.max(4, 9));
        TestKit.expectEquals("max(5, 5)", 5, Operators.max(5, 5));

        TestKit.expectEquals("clamp(15, 0, 5)", 5, Operators.clamp(15, 0, 5));
        TestKit.expectEquals("clamp(-2, 0, 5)", 0, Operators.clamp(-2, 0, 5));
        TestKit.expectEquals("clamp(3, 0, 5)", 3, Operators.clamp(3, 0, 5));
        TestKit.done();
    }
}
