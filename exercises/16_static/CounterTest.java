import dev.javaling.testkit.TestKit;

public class CounterTest {

    public static void main(String[] args) {
        TestKit.expectEquals("一開始沒有物件", 0, Counter.instances());

        Counter a = new Counter();
        a.increment();
        a.increment();

        Counter b = new Counter();
        b.increment();

        TestKit.expectEquals("a 的值", 2, a.value());
        TestKit.expectEquals("b 的值（不該被 a 影響）", 1, b.value());
        TestKit.expectEquals("總共建立兩個物件", 2, Counter.instances());
        TestKit.done();
    }
}
