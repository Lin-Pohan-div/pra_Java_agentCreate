import dev.javaling.testkit.TestKit;

public class VariablesTest {

    public static void main(String[] args) {
        TestKit.expectEquals("sum(3, 4)", 7, Variables.sum(3, 4));
        TestKit.expectEquals("sum(-2, 2)", 0, Variables.sum(-2, 2));
        TestKit.expectEquals("average(7, 2)", 4.5, Variables.average(7, 2));
        TestKit.expectEquals("average(4, 4)", 4.0, Variables.average(4, 4));
        TestKit.expectEquals("describe(3)", "n=3", Variables.describe(3));
        TestKit.done();
    }
}
