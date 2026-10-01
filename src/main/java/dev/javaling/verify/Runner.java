package dev.javaling.verify;

import dev.javaling.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/** 在獨立 JVM 中執行練習的測試類別，擷取輸出。 */
public final class Runner {

    private Runner() {
    }

    public record Result(int exitCode, String output, boolean timedOut) {

        public boolean passed() {
            return !timedOut && exitCode == 0 && output.contains(Constants.PASS_MARKER);
        }

        /** 測試程式的實際輸出（去掉標記與多餘空行）。 */
        public String cleanOutput() {
            StringBuilder sb = new StringBuilder();
            for (String line : output.split("\\R")) {
                String trimmed = line.strip();
                if (trimmed.isEmpty()
                        || trimmed.equals(Constants.PASS_MARKER)
                        || trimmed.startsWith(Constants.FAIL_MARKER)) {
                    continue;
                }
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString().strip();
        }
    }

    public static Result run(Path outDir, String testClassName, String classpath) {
        String javaExe = Path.of(System.getProperty("java.home"), "bin",
                isWindows() ? "java.exe" : "java").toString();

        String fullClasspath = classpath == null || classpath.isBlank()
                ? outDir.toString()
                : outDir + File.pathSeparator + classpath;

        ProcessBuilder pb = new ProcessBuilder(
                javaExe,
                "-Dfile.encoding=UTF-8",
                "-Dstdout.encoding=UTF-8",
                "-Dstderr.encoding=UTF-8",
                "-cp", fullClasspath,
                testClassName);
        pb.redirectErrorStream(true);

        Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            return new Result(-1, "無法啟動測試程序：" + e.getMessage(), false);
        }

        // 輸出改在另一條執行緒讀：不印任何東西的無窮迴圈永遠不會關閉串流，
        // 若在主執行緒 readLine() 會卡死，waitFor 的逾時就永遠輪不到。
        StringBuffer output = new StringBuffer();
        Thread reader = new Thread(() -> {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = in.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                output.append("讀取測試輸出失敗：").append(e.getMessage()).append(System.lineSeparator());
            }
        }, "javalings-test-output");
        reader.setDaemon(true);
        reader.start();

        try {
            if (!process.waitFor(Constants.TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                process.destroyForcibly();
                reader.join(1_000);
                return new Result(-1, output.toString(), true);
            }
            reader.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            process.destroyForcibly();
            return new Result(-1, "測試被中斷。", false);
        }

        return new Result(process.exitValue(), output.toString(), false);
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }
}
