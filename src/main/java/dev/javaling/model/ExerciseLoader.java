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

    public static List<Exercise> load(Path root) throws IOException {
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
                Exercise ex = read(dir);
                if (ex != null) {
                    result.add(ex);
                }
            }
        }
        return result;
    }

    private static Exercise read(Path dir) throws IOException {
        String id = dir.getFileName().toString();
        Path meta = dir.resolve("exercise.json");
        if (!Files.isRegularFile(meta)) {
            return null;
        }
        Map<String, Object> json = Json.parseObject(Files.readString(meta, StandardCharsets.UTF_8));

        Path source = null;
        Path test = null;
        try (Stream<Path> files = Files.list(dir)) {
            for (Path f : files.filter(Files::isRegularFile).sorted().toList()) {
                String name = f.getFileName().toString();
                if (!name.endsWith(".java")) {
                    continue;
                }
                if (name.endsWith("Test.java")) {
                    test = f;
                } else {
                    source = f;
                }
            }
        }
        if (source == null || test == null) {
            return null;
        }

        int order = 0;
        String digits = id.replaceAll("^(\\d+).*$", "$1");
        if (!digits.isEmpty()) {
            order = Integer.parseInt(digits);
        }

        @SuppressWarnings("unchecked")
        List<Object> rawTopics = (List<Object>) json.getOrDefault("topics", List.of());
        List<String> topics = rawTopics.stream().map(String::valueOf).toList();

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
