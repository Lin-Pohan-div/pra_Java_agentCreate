import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public static Map<String, Integer> countWords(String text) {
        Map<String, Integer> counts = new HashMap<>();
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
        if (counts.isEmpty()) {
            return 0.0;
        }
        return (double) totalWords(counts) / counts.size();
    }
}
