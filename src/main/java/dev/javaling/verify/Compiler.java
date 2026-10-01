package dev.javaling.verify;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/** 用 JDK 內建 compiler API 編譯練習（不依賴外部 javac 命令）。 */
public final class Compiler {

    private Compiler() {
    }

    public record Result(boolean success, List<String> errors, List<String> warnings) {

        public String errorReport() {
            return String.join(System.lineSeparator(), errors);
        }
    }

    public static Result compile(Path outDir, List<Path> sources, String classpath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return new Result(false,
                    List.of("找不到 Java 編譯器，請確認執行環境是 JDK 而不是 JRE。"), List.of());
        }

        try {
            clean(outDir);
            Files.createDirectories(outDir);
        } catch (IOException e) {
            return new Result(false, List.of("無法準備輸出資料夾：" + e.getMessage()), List.of());
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager =
                     compiler.getStandardFileManager(diagnostics, Locale.getDefault(), StandardCharsets.UTF_8)) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(outDir.toFile()));
            Iterable<? extends JavaFileObject> units =
                    fileManager.getJavaFileObjectsFromFiles(sources.stream().map(Path::toFile).toList());

            List<String> options = new ArrayList<>(List.of("-encoding", "UTF-8", "-Xlint:none"));
            if (classpath != null && !classpath.isBlank()) {
                options.addAll(List.of("-classpath", classpath));
            }

            Writer sink = new Writer() {
                @Override
                public void write(char[] cbuf, int off, int len) {
                    // 忽略編譯器直接寫到 stdout 的內容
                }

                @Override
                public void flush() {
                }

                @Override
                public void close() {
                }
            };

            boolean ok = compiler.getTask(sink, fileManager, diagnostics, options, null, units).call();

            List<String> errors = new ArrayList<>();
            List<String> warnings = new ArrayList<>();
            for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
                String line = format(d);
                if (d.getKind() == Diagnostic.Kind.ERROR) {
                    errors.add(line);
                } else if (d.getKind() == Diagnostic.Kind.WARNING
                        || d.getKind() == Diagnostic.Kind.MANDATORY_WARNING) {
                    warnings.add(line);
                }
            }
            return new Result(ok && errors.isEmpty(), errors, warnings);
        } catch (IOException e) {
            return new Result(false, List.of("編譯失敗：" + e.getMessage()), List.of());
        }
    }

    private static String format(Diagnostic<? extends JavaFileObject> d) {
        StringBuilder sb = new StringBuilder();
        JavaFileObject src = d.getSource();
        if (src != null) {
            sb.append(src.getName());
            if (d.getLineNumber() > 0) {
                sb.append(':').append(d.getLineNumber());
                if (d.getColumnNumber() > 0) {
                    sb.append(':').append(d.getColumnNumber());
                }
            }
            sb.append(": ");
        }
        sb.append(d.getKind().name().toLowerCase(Locale.ROOT)).append(": ");
        sb.append(d.getMessage(Locale.getDefault()));
        return sb.toString();
    }

    /** 清空輸出資料夾，避免舊的 .class 檔干擾結果。 */
    private static void clean(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(dir)) {
            for (Path p : walk.sorted(Comparator.reverseOrder()).toList()) {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException ignored) {
                    // 檔案被鎖住就跳過
                }
            }
        }
    }
}
