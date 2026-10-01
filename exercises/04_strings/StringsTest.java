import dev.javaling.testkit.TestKit;

public class StringsTest {

    public static void main(String[] args) {
        TestKit.expectEquals("repeat(\"ab\", 3)", "ababab", Strings.repeat("ab", 3));
        TestKit.expectEquals("repeat(\"x\", 1)", "x", Strings.repeat("x", 1));
        TestKit.expectEquals("repeat(\"x\", 0)", "", Strings.repeat("x", 0));

        TestKit.expectTrue("isPalindrome(\"racecar\")", Strings.isPalindrome("racecar"));
        TestKit.expectTrue("isPalindrome(\"a\")", Strings.isPalindrome("a"));
        TestKit.expectFalse("isPalindrome(\"abc\")", Strings.isPalindrome("abc"));
        TestKit.expectTrue("isPalindrome(\"\") 空字串也算迴文", Strings.isPalindrome(""));

        TestKit.expectEquals("shout(\"hi\")", "HI", Strings.shout("hi"));
        TestKit.done();
    }
}
