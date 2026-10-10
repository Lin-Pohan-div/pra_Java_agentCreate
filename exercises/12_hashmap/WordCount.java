import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public static Map<String, Integer> countWords(String text) {
        Map<String, Integer> counts = new HashMap<>();
        // TODO: 依空白切字後統計每個字出現幾次（不計大小寫）
        for (String word : text.split("\\s+")) {
            if (word.isEmpty()) {
                continue;
            }
            String key = word.toLowerCase();
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }
        return counts;
    }

    public static int totalWords(Map<String, Integer> counts) {
        int total = 0;
        for (int n : counts.values()) {
            total += n;
        }
        return total;
    }

    public static double average(Map<String, Integer> counts) {
        int total = totalWords(counts);
        // TODO: 平均每個字出現幾次，{a:2,b:1} 要得到 1.5
        return (double) total / counts.size();
    }
}
