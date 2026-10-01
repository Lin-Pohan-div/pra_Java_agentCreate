import dev.javaling.testkit.TestKit;

public class RectangleTest {

    public static void main(String[] args) {
        Rectangle r = new Rectangle(2, 3);
        TestKit.expectClose("area()", 6.0, r.area());
        TestKit.expectClose("perimeter()", 10.0, r.perimeter());
        TestKit.expectFalse("isSquare()", r.isSquare());

        r.scale(2);
        TestKit.expectClose("scale 後寬", 4.0, r.getWidth());
        TestKit.expectClose("scale 後高", 6.0, r.getHeight());

        TestKit.expectTrue("2x2 是正方形", new Rectangle(2, 2).isSquare());
        TestKit.done();
    }
}
