import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentStats {

    public static List<Integer> passing(List<Integer> scores, int threshold) {
        return scores.stream()
                .filter(s -> s > threshold)
                .collect(Collectors.toList());
    }

    public static Map<Boolean, Long> partition(List<Integer> scores, int threshold) {
        return scores.stream()
                .collect(Collectors.partitioningBy(
                        s -> s >= threshold, Collectors.counting()));
    }

    public static String shoutNames(List<String> names) {
        return names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
    }
}
