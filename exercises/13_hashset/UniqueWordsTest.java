import dev.javaling.testkit.TestKit;

import java.util.List;

public class UniqueWordsTest {

    public static void main(String[] args) {
        TestKit.expectEquals("unique(\"a A b a\")",
                List.of("a", "b"), List.copyOf(UniqueWords.unique("a A b a")));
        TestKit.expectEquals("unique(\"X  x \")",
                List.of("x"), List.copyOf(UniqueWords.unique("X  x ")));

        TestKit.expectEquals("countDistinct(\"a a a\")", 1, UniqueWords.countDistinct("a a a"));
        TestKit.expectEquals("countDistinct(\"a b\")", 2, UniqueWords.countDistinct("a b"));

        TestKit.expectFalse("hasDuplicates([1,2,3])",
                UniqueWords.hasDuplicates(new int[]{1, 2, 3}));
        TestKit.expectTrue("hasDuplicates([1,1])",
                UniqueWords.hasDuplicates(new int[]{1, 1}));
        TestKit.expectFalse("hasDuplicates([])", UniqueWords.hasDuplicates(new int[0]));
        TestKit.done();
    }
}
