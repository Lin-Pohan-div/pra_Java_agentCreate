import dev.javaling.testkit.TestKit;

/**
 * 這份測試不需要修改，是驗收標準。
 * 把它和 Hello.java 一起編譯後執行。
 */
public class HelloTest {

    public static void main(String[] args) {
        TestKit.expectEquals("greet(\"World\")", "Hello, World!", Hello.greet("World"));
        TestKit.expectEquals("greet(\"Java\")", "Hello, Java!", Hello.greet("Java"));
        TestKit.expectEquals("greet()", "Hello, Javalings!", Hello.greet());
        TestKit.done();
    }
}
