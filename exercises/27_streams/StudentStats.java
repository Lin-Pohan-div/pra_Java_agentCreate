// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentStats {

    public static List<Integer> passing(List<Integer> scores, int threshold) {
        // TODO: 篩出「嚴格大於」門檻的分數，passing([50,70,90], 70) 要得到 [90]
        return scores.stream()
                .filter(s -> s >= threshold)
                .collect(Collectors.toList());
    }

    public static Map<Boolean, Long> partition(List<Integer> scores, int threshold) {
        // TODO: 分成「及格」與「不及格」兩組並統計人數
        //       partition([50,70,90], 70) 要得到 {false=1, true=2}
        return scores.stream()
                .collect(Collectors.partitioningBy(s -> s));
    }

    public static String shoutNames(List<String> names) {
        // TODO: 名字全部轉大寫後用 ", " 串起來，shoutNames([anna, bob]) 應該是 "ANNA, BOB"
        return names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
    }
}
