package dev.javaling.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** 學習進度：哪些練習已完成、現在做到哪一題。 */
public final class AppState {

    private static final String DIR = ".javalings";
    private static final String FILE = "progress.txt";

    private final Path file;
    private final Set<String> done = new LinkedHashSet<>();
    private String current;

    private AppState(Path file) {
        this.file = file;
    }

    public static AppState load(Path root, List<Exercise> exercises) throws IOException {
        Path file = root.resolve(DIR).resolve(FILE);
        AppState state = new AppState(file);
        if (Files.isRegularFile(file)) {
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                String trimmed = line.trim();
                if (trimmed.startsWith("current:")) {
                    state.current = trimmed.substring("current:".length()).trim();
                } else if (trimmed.startsWith("done:")) {
                    state.done.add(trimmed.substring("done:".length()).trim());
                }
            }
        }
        if (state.current == null || exercises.stream().noneMatch(e -> e.id().equals(state.current))) {
            state.current = exercises.stream()
                    .filter(e -> !state.done.contains(e.id()))
                    .findFirst()
                    .orElse(exercises.isEmpty() ? null : exercises.get(0))
                    .id();
        }
        return state;
    }

    public boolean isDone(String id) {
        return done.contains(id);
    }

    public Set<String> done() {
        return done;
    }

    public String current() {
        return current;
    }

    public void setCurrent(String id) {
        this.current = id;
    }

    /** 標記完成，回傳是否為第一次完成。 */
    public boolean markDone(String id) {
        return done.add(id);
    }

    public void unmarkDone(String id) {
        done.remove(id);
    }

    public void resetAll(List<Exercise> exercises) {
        done.clear();
        for (Exercise ex : exercises) {
            deleteArtifact(ex);
        }
        current = exercises.isEmpty() ? null : exercises.get(0).id();
    }

    /** 清掉編譯產物，讓練習回到「未修改」的乾淨狀態。 */
    private void deleteArtifact(Exercise ex) {
        try {
            Path out = ex.dir().resolve(".javalings-out");
            if (Files.exists(out)) {
                try (var walk = Files.walk(out)) {
                    walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                        } catch (IOException ignored) {
                            // 刪不掉就算了，不影響流程
                        }
                    });
                }
            }
        } catch (IOException ignored) {
            // 同上
        }
    }

    public List<String> notDone(List<Exercise> exercises) {
        List<String> result = new ArrayList<>();
        for (Exercise ex : exercises) {
            if (!done.contains(ex.id())) {
                result.add(ex.id());
            }
        }
        return result;
    }

    public void save() throws IOException {
        Files.createDirectories(file.getParent());
        StringBuilder sb = new StringBuilder();
        sb.append("# javalings progress - 請勿手動編輯\n");
        sb.append("current:").append(current == null ? "" : current).append('\n');
        for (String id : done) {
            sb.append("done:").append(id).append('\n');
        }
        Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
    }
}
