import dev.javaling.testkit.TestKit;

public class PrintableTest {

    public static void main(String[] args) {
        Tag tag = new Tag("x");
        TestKit.expectEquals("Tag.label()", "<x>", tag.label());
        TestKit.expectEquals("Tag.framed()", "[<x>]", tag.framed());

        Printable p = Printable.of("y");
        TestKit.expectEquals("Printable.of(\"y\").label()", "y", p.label());
        TestKit.expectEquals("Printable.of(\"y\").framed()", "[y]", p.framed());

        // 多型：介面型別的參考可以指向實作類別
        Printable[] items = {new Tag("a"), Printable.of("b")};
        TestKit.expectEquals("多型", "[<a>]", items[0].framed());
        TestKit.expectEquals("多型", "[b]", items[1].framed());
        TestKit.done();
    }
}
