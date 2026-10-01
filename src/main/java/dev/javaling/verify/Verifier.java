package dev.javaling.verify;

import dev.javaling.Constants;
import dev.javaling.model.Exercise;

import java.nio.file.Path;
import java.util.List;

/** 編譯 + 執行測試，得到一個練習的驗收結果。 */
public final class Verifier {

    private Verifier() {
    }

    public enum Status {
        PASSED,
        COMPILE_ERROR,
        TEST_FAILED,
        INTERNAL_ERROR
    }

    public record Result(
            Status status,
            List<String> errors,
            String testOutput,
            String message) {

        public boolean passed() {
            return status == Status.PASSED;
        }
    }

    public static Result verify(Exercise exercise, String selfClasspath) {
        Path outDir = exercise.dir().resolve(".javalings-out");

        Compiler.Result compiled = Compiler.compile(
                outDir,
                List.of(exercise.source(), exercise.test()),
                selfClasspath);

        if (!compiled.success()) {
            String first = compiled.errors().isEmpty() ? "編譯失敗。" : compiled.errors().get(0);
            return new Result(Status.COMPILE_ERROR, compiled.errors(), "",
                    first);
        }

        Runner.Result run = Runner.run(outDir, exercise.testClassName(), selfClasspath);
        if (run.passed()) {
            return new Result(Status.PASSED, List.of(), run.cleanOutput(), "全部測試通過。");
        }
        if (run.timedOut()) {
            return new Result(Status.INTERNAL_ERROR, List.of(), run.cleanOutput(),
                    "測試逾時（超過 " + Constants.TEST_TIMEOUT_MS / 1000 + " 秒），可能有無窮迴圈。");
        }
        return new Result(Status.TEST_FAILED, List.of(), run.cleanOutput(),
                run.cleanOutput().isEmpty() ? "測試沒有輸出結果，可能缺少 TestKit.done()。" : "測試失敗。");
    }
}
