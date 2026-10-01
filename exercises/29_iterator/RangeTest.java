import dev.javaling.testkit.TestKit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RangeTest {

    private static List<Integer> collect(Range range) {
        List<Integer> out = new ArrayList<>();
        for (int v : range) {
            out.add(v);
        }
        return out;
    }

    public static void main(String[] args) {
        TestKit.expectEquals("Range(0, 5, 1)",
                List.of(0, 1, 2, 3, 4), collect(new Range(0, 5, 1)));
        TestKit.expectEquals("Range(0, 10, 3)",
                List.of(0, 3, 6, 9), collect(new Range(0, 10, 3)));
        TestKit.expectEquals("Range(5, 0, -2) 遞減",
                List.of(5, 3, 1), collect(new Range(5, 0, -2)));
        TestKit.expectEquals("Range(0, 0, 1) 是空的", List.of(), collect(new Range(0, 0, 1)));

        int sum = 0;
        for (int v : new Range(0, 5, 1)) {
            sum += v;
        }
        TestKit.expectEquals("for-each 加總", 10, sum);

        Iterator<Integer> it = new Range(0, 1, 1).iterator();
        it.next();
        TestKit.expectThrows("取完要丟 NoSuchElementException",
                NoSuchElementException.class, it::next);
        TestKit.done();
    }
}
