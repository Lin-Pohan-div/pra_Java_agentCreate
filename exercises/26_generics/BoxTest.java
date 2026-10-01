import dev.javaling.testkit.TestKit;

public class BoxTest {

    public static void main(String[] args) {
        Box<String> box = new Box<>();
        TestKit.expectTrue("新建時是空的", box.isEmpty());

        box.set("hello");
        TestKit.expectFalse("設定後不是空的", box.isEmpty());
        TestKit.expectEquals("get()", "hello", box.get());

        TestKit.expectEquals("map 到長度", 5, box.map(String::length));
        TestKit.expectEquals("map 到大寫", "HELLO", box.map(String::toUpperCase));

        Box<Integer> numbers = new Box<>();
        numbers.set(21);
        TestKit.expectEquals("泛型是 Integer", 42, numbers.map(v -> v * 2));
        TestKit.done();
    }
}
