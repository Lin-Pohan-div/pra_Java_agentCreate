import dev.javaling.testkit.TestKit;

public class MatrixTest {

    public static void main(String[] args) {
        int[][] id = Matrix.identity(3);
        TestKit.expectEquals("identity(3) 大小", 3, id.length);
        TestKit.expectEquals("identity(3)[0][0]", 1, id[0][0]);
        TestKit.expectEquals("identity(3)[0][1]", 0, id[0][1]);
        TestKit.expectEquals("identity(3)[1][1]", 1, id[1][1]);
        TestKit.expectEquals("identity(3)[2][0]", 0, id[2][0]);
        TestKit.expectEquals("identity(3)[2][2]", 1, id[2][2]);

        TestKit.expectEquals("identity(3) 總和", 3, Matrix.sumOfAll(id));
        TestKit.expectEquals("sumMainDiagonal([[1,2],[3,4]])",
                5, Matrix.sumMainDiagonal(new int[][]{{1, 2}, {3, 4}}));
        TestKit.done();
    }
}
