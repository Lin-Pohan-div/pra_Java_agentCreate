public class Rectangle {

    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }

    public boolean isSquare() {
        return width == height;
    }

    public void scale(double factor) {
        this.width = width * factor;
        this.height = height * factor;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
