package dev.javaling.model;

import dev.javaling.util.Json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/** 從 {@code exercises/} 資料夾載入所有練習。 */
public final class ExerciseLoader {

    private ExerciseLoader() {
    }

    /**
     * 載入所有練習。缺少必要檔案的資料夾會靜默略過；檔案數量不明確（例如兩個待修檔）的資料夾
     * 也會略過，但原因會加進 {@code warnings}，讓出題者知道為什麼題目沒有出現。
     */
    public static List<Exercise> load(Path root, List<String> warnings) throws IOException {
        if (!Files.isDirectory(root)) {
            throw new IOException("找不到練習資料夾：" + root.toAbsolutePath());
        }
        List<Exercise> result = new ArrayList<>();
        try (Stream<Path> children = Files.list(root)) {
            List<Path> dirs = children
                    .filter(Files::isDirectory)
                    .filter(p -> !p.getFileName().toString().startsWith("."))
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .toList();
            for (Path dir : dirs) {
                Exercise ex = read(dir, warnings);
                if (ex != null) {
                    result.add(ex);
                }
            }
        }
        return result;
    }

    private static Exercise read(Path dir, List<String> warnings) throws IOException {
        String id = dir.getFileName().toString();
        Path meta = dir.resolve("exercise.json");
        if (!Files.isRegularFile(meta)) {
            return null;
        }

        List<Path> sources = new ArrayList<>();
        List<Path> tests = new ArrayList<>();
        try (Stream<Path> files = Files.list(dir)) {
            for (Path f : files.filter(Files::isRegularFile).sorted().toList()) {
                String name = f.getFileName().toString();
                if (!name.endsWith(".java")) {
                    continue;
                }
                if (name.endsWith("Test.java")) {
                    tests.add(f);
                } else {
                    sources.add(f);
                }
            }
        }
        if (sources.isEmpty() || tests.isEmpty()) {
            return null;
        }
        if (sources.size() > 1 || tests.size() > 1) {
            warnings.add("略過 " + id + "：每題只能有一個待修的 .java 和一個 *Test.java，目前有 "
                    + sources.size() + " 個待修檔、" + tests.size() + " 個測試檔。");
            return null;
        }
        Path source = sources.get(0);
        Path test = tests.get(0);

        Map<String, Object> json;
        try {
            json = Json.parseObject(Files.readString(meta, StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new IOException(id + "/exercise.json 格式錯誤：" + e.getMessage(), e);
        }

        int order = 0;
        String digits = id.replaceAll("^(\\d+).*$", "$1");
        if (!digits.isEmpty()) {
            order = Integer.parseInt(digits);
        }

        Object rawTopics = json.getOrDefault("topics", List.of());
        List<String> topics = rawTopics instanceof List<?> list
                ? list.stream().map(String::valueOf).toList()
                : List.of(String.valueOf(rawTopics));

        return new Exercise(
                id,
                String.valueOf(json.getOrDefault("name", id)),
                dir,
                source,
                test,
                order,
                String.valueOf(json.getOrDefault("difficulty", "★")),
                topics,
                String.valueOf(json.getOrDefault("hint", "")));
    }
}
