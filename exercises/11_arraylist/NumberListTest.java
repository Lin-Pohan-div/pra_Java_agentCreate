import dev.javaling.testkit.TestKit;

import java.util.ArrayList;
import java.util.List;

public class NumberListTest {

    public static void main(String[] args) {
        TestKit.expectEquals("ofRange(1, 3)",
                List.of(1, 2, 3), NumberList.ofRange(1, 3));
        TestKit.expectEquals("ofRange(2, 2)",
                List.of(2), NumberList.ofRange(2, 2));

        ArrayList<Integer> input = new ArrayList<>(List.of(1, 2, 3));
        TestKit.expectEquals("total([1,2,3])", 6, NumberList.total(input));
        TestKit.expectEquals("total([])", 0, NumberList.total(new ArrayList<Integer>()));

        TestKit.expectEquals("doubled([1,2])", List.of(2, 4), NumberList.doubled(new ArrayList<>(List.of(1, 2))));
        NumberList.doubled(input);
        TestKit.expectEquals("doubled 不可改動原清單", List.of(1, 2, 3), input);

        TestKit.expectEquals("evens([1,2,3,4])",
                List.of(2, 4), NumberList.evens(new ArrayList<>(List.of(1, 2, 3, 4))));
        TestKit.expectEquals("evens([1,3])", List.of(), NumberList.evens(new ArrayList<>(List.of(1, 3))));
        TestKit.done();
    }
}
