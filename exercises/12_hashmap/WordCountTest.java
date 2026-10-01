import dev.javaling.testkit.TestKit;

import java.util.Map;

public class WordCountTest {

    public static void main(String[] args) {
        Map<String, Integer> counts = WordCount.countWords("a A b a");
        TestKit.expectEquals("不計大小寫後 a 的次數", 3, counts.get("a"));
        TestKit.expectEquals("b 的次數", 1, counts.get("b"));
        TestKit.expectEquals("不同字的數量", 2, counts.size());
        TestKit.expectEquals("總字數", 4, WordCount.totalWords(counts));
        TestKit.expectEquals("平均", 2.0, WordCount.average(counts));

        TestKit.expectEquals("空字串",
                0, WordCount.totalWords(WordCount.countWords("")));
        TestKit.done();
    }
}
