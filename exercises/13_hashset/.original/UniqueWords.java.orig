import java.util.LinkedHashSet;
import java.util.Set;

public class UniqueWords {

    public static Set<String> unique(String text) {
        // TODO: 依空白切字，去掉前後空白並轉小寫後去重
        Set<String> out = new LinkedHashSet<>();
        for (String word : text.split("\\s+")) {
            out.add(word.toLowerCase());
        }
        return out;
    }

    public static int countDistinct(String text) {
        return unique(text).size();
    }

    public static boolean hasDuplicates(int[] values) {
        // TODO: 有重複回 true，hasDuplicates([1,2,3]) 應該是 false
        Set<Integer> seen = new LinkedHashSet<>();
        boolean duplicated = false;
        for (int v : values) {
            if (seen.add(v)) {
                duplicated = true;
            }
        }
        return duplicated;
    }
}
