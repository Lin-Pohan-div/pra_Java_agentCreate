// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LineCounter {

    public static int countLines(String path) throws IOException {
        // TODO: 用 try-with-resources 開檔，逐行累加數量
        int lines = 0;
        return lines;
    }

    public static String firstLine(String path) throws IOException {
        // TODO: 回傳檔案的第一行（這是 try-with-resources 的參考寫法）
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)) {
            return reader.readLine();
        }
    }
}
