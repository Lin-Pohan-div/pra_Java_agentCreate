import dev.javaling.testkit.TestKit;

public class LoopsTest {

    public static void main(String[] args) {
        TestKit.expectEquals("sumTo(5)", 15, Loops.sumTo(5));
        TestKit.expectEquals("sumTo(1)", 1, Loops.sumTo(1));
        TestKit.expectEquals("sumTo(0)", 0, Loops.sumTo(0));

        TestKit.expectEquals("countLetters(\"a1b!\")", 2, Loops.countLetters("a1b!"));
        TestKit.expectEquals("countLetters(\"abc\")", 3, Loops.countLetters("abc"));
        TestKit.expectEquals("countLetters(\"\")", 0, Loops.countLetters(""));
        TestKit.expectEquals("countLetters(\"123\")", 0, Loops.countLetters("123"));

        TestKit.expectEquals("countDown(3)", 3, Loops.countDown(3));
        TestKit.expectEquals("countDown(0)", 0, Loops.countDown(0));
        TestKit.done();
    }
}
