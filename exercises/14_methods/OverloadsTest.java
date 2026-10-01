import dev.javaling.testkit.TestKit;

public class OverloadsTest {

    public static void main(String[] args) {
        TestKit.expectEquals("add(2, 3)", 5, Overloads.add(2, 3));
        TestKit.expectEquals("add(2.5, 0.5)", 3.0, Overloads.add(2.5, 0.5));

        TestKit.expectEquals("describe(7)", "int 7", Overloads.describe(7));
        TestKit.expectEquals("describe(\"hi\")", "hi", Overloads.describe("hi"));

        TestKit.expectEquals("sum(1,2,3)", 6, Overloads.sum(1, 2, 3));
        TestKit.expectEquals("sum()", 0, Overloads.sum());
        TestKit.expectEquals("sum(5)", 5, Overloads.sum(5));
        TestKit.done();
    }
}
