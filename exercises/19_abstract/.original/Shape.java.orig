// 先讀同資料夾的 README.md，再動手修改這個檔案。

public abstract class Shape {

    // TODO: 每種圖形都必須自己實作 area()，這裡只宣告、不實作
    public abstract double area();

    public String describe() {
        return getClass().getSimpleName() + "(" + area() + ")";
    }

    public static double totalArea(Shape[] shapes) {
        // TODO: 回傳所有圖形面積的總和
        double total = 0;
        for (Shape s : shapes) {
            total += s.area() / 2;
        }
        return total;
    }
}

class Square extends Shape {

    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        // TODO: 正方形面積 = 邊長 × 邊長，Square(2) 的面積應該是 4.0
        return side * side
    }
}

class Circle extends Shape {

    private final double radius;

    public Circle(double radius) {
        // TODO: 把參數 radius 存進這個物件的欄位
        radius = radius;
    }

    @Override
    public double area() {
        // TODO: 圓面積 = π × 半徑²，Circle(1) 的面積應該是 Math.PI
        return Math.PI * radius * radius;
    }
}
