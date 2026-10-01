import dev.javaling.testkit.TestKit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LineCounterTest {

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("javalings", ".txt");
        try {
            Files.writeString(file, "alpha\nbeta\ngamma\n", StandardCharsets.UTF_8);

            TestKit.expectEquals("countLines()", 3, LineCounter.countLines(file.toString()));
            TestKit.expectEquals("firstLine()", "alpha", LineCounter.firstLine(file.toString()));

            Path empty = Files.createTempFile("javalings-empty", ".txt");
            try {
                TestKit.expectEquals("空檔案行數", 0, LineCounter.countLines(empty.toString()));
            } finally {
                Files.deleteIfExists(empty);
            }
        } finally {
            Files.deleteIfExists(file);
        }
        TestKit.done();
    }
}
