import dev.javaling.testkit.TestKit;

public class ShapeTest {

    public static void main(String[] args) {
        Shape square = new Square(2);
        Shape circle = new Circle(1);

        TestKit.expectClose("Square(2).area()", 4.0, square.area());
        TestKit.expectClose("Circle(1).area()", Math.PI, circle.area());
        TestKit.expectEquals("describe()", "Square(4.0)", square.describe());

        TestKit.expectClose("totalArea",
                4.0 + Math.PI, Shape.totalArea(new Shape[]{square, circle}));
        TestKit.done();
    }
}
