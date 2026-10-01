import dev.javaling.testkit.TestKit;

public class IfElseTest {

    public static void main(String[] args) {
        TestKit.expectEquals("grade(95)", "A", IfElse.grade(95));
        TestKit.expectEquals("grade(90)", "A", IfElse.grade(90));
        TestKit.expectEquals("grade(85)", "B", IfElse.grade(85));
        TestKit.expectEquals("grade(70)", "C", IfElse.grade(70));
        TestKit.expectEquals("grade(60)", "F", IfElse.grade(60));

        TestKit.expectEquals("sign(5)", "positive", IfElse.sign(5));
        TestKit.expectEquals("sign(-5)", "negative", IfElse.sign(-5));
        TestKit.expectEquals("sign(0)", "zero", IfElse.sign(0));

        TestKit.expectTrue("isLeapYear(2024)", IfElse.isLeapYear(2024));
        TestKit.expectFalse("isLeapYear(1900)", IfElse.isLeapYear(1900));
        TestKit.expectTrue("isLeapYear(2000)", IfElse.isLeapYear(2000));
        TestKit.expectFalse("isLeapYear(2023)", IfElse.isLeapYear(2023));
        TestKit.done();
    }
}
