import java.util.LinkedHashSet;
import java.util.Set;

public class UniqueWords {

    public static Set<String> unique(String text) {
        Set<String> out = new LinkedHashSet<>();
        for (String word : text.split("\\s+")) {
            if (word.isEmpty()) {
                continue;
            }
            out.add(word.toLowerCase());
        }
        return out;
    }

    public static int countDistinct(String text) {
        return unique(text).size();
    }

    public static boolean hasDuplicates(int[] values) {
        Set<Integer> seen = new LinkedHashSet<>();
        for (int v : values) {
            if (!seen.add(v)) {
                return true;
            }
        }
        return false;
    }
}
