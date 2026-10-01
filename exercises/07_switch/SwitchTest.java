import dev.javaling.testkit.TestKit;

public class SwitchTest {

    public static void main(String[] args) {
        TestKit.expectEquals("dayName(1)", "Monday", Switch.dayName(1));
        TestKit.expectEquals("dayName(7)", "Sunday", Switch.dayName(7));
        TestKit.expectEquals("dayName(3)", "Unknown", Switch.dayName(3));

        TestKit.expectTrue("isVowel('a')", Switch.isVowel('a'));
        TestKit.expectTrue("isVowel('u')", Switch.isVowel('u'));
        TestKit.expectFalse("isVowel('x')", Switch.isVowel('x'));
        TestKit.expectFalse("isVowel('1')", Switch.isVowel('1'));

        TestKit.expectEquals("describe(0)", "zero", Switch.describe(0));
        TestKit.expectEquals("describe(1)", "one", Switch.describe(1));
        TestKit.expectEquals("describe(2)", "two", Switch.describe(2));
        TestKit.expectEquals("describe(9)", "many", Switch.describe(9));
        TestKit.done();
    }
}
