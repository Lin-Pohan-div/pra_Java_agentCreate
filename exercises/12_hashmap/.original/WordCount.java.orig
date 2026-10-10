// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public static Map<String, Integer> countWords(String text) {
        Map<String, Integer> counts = new HashMap<>();
        // TODO: 依空白切字後統計每個字出現幾次（不計大小寫）
        for (String word : text.split("\\s+")) {
            counts.put(word, counts.get(word) + 1);
        }
        return counts;
    }

    public static int totalWords(Map<String, Integer> counts) {
        // TODO: 把每個字的出現次數加起來，totalWords({a:2,b:1}) 應該是 3
        int total = 0;
        for (int n : counts.values()) {
            total += n;
        }
        return total;
    }

    public static double average(Map<String, Integer> counts) {
        int total = totalWords(counts);
        // TODO: 平均每個字出現幾次，{a:2,b:1} 要得到 1.5
        return total / counts.size();
    }
}
