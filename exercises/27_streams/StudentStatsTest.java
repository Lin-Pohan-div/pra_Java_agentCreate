import dev.javaling.testkit.TestKit;

import java.util.List;
import java.util.Map;

public class StudentStatsTest {

    public static void main(String[] args) {
        List<Integer> scores = List.of(50, 70, 90);

        TestKit.expectEquals("passing([50,70,90], 70)",
                List.of(90), StudentStats.passing(scores, 70));
        TestKit.expectEquals("passing(門檻 0)",
                List.of(50, 70, 90), StudentStats.passing(scores, 0));

        TestKit.expectEquals("partition",
                Map.of(Boolean.FALSE, 1L, Boolean.TRUE, 2L),
                StudentStats.partition(scores, 70));
        TestKit.expectEquals("partition 空清單",
                Map.of(Boolean.FALSE, 0L, Boolean.TRUE, 0L),
                StudentStats.partition(List.of(), 70));

        TestKit.expectEquals("shoutNames",
                "ANNA, BOB", StudentStats.shoutNames(List.of("anna", "bob")));
        TestKit.done();
    }
}
