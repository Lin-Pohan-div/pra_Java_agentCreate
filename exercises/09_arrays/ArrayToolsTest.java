import dev.javaling.testkit.TestKit;

public class ArrayToolsTest {

    public static void main(String[] args) {
        TestKit.expectEquals("sum([1,2,3])", 6, ArrayTools.sum(new int[]{1, 2, 3}));
        TestKit.expectEquals("sum([])", 0, ArrayTools.sum(new int[0]));

        TestKit.expectEquals("max([4,9,2])", 9, ArrayTools.max(new int[]{4, 9, 2}));
        TestKit.expectEquals("max([-3])", -3, ArrayTools.max(new int[]{-3}));

        int[] input = {1, 2, 3};
        TestKit.expectEquals("doubled([1,2,3])",
                new int[]{2, 4, 6}, ArrayTools.doubled(input));
        TestKit.expectEquals("doubled 不可改動原陣列",
                new int[]{1, 2, 3}, input);
        TestKit.done();
    }
}
