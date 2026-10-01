import dev.javaling.testkit.TestKit;

public class GridTest {

    public static void main(String[] args) {
        TestKit.expectEquals("row(2)", "1*2=2 2*2=4 3*2=6", Grid.row(2));
        TestKit.expectEquals("row(3)", "1*3=3 2*3=6 3*3=9", Grid.row(3));

        TestKit.expectEquals("countPairs(4)", 6, Grid.countPairs(4));
        TestKit.expectEquals("countPairs(1)", 0, Grid.countPairs(1));
        TestKit.expectEquals("countPairs(0)", 0, Grid.countPairs(0));

        TestKit.expectEquals("sumMultiples(10)", 18, Grid.sumMultiples(10));
        TestKit.expectEquals("sumMultiples(4)", 3, Grid.sumMultiples(4));
        TestKit.done();
    }
}
