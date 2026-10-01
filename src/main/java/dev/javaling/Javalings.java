package dev.javaling;

import dev.javaling.cli.Console;
import dev.javaling.cli.Table;
import dev.javaling.model.AppState;
import dev.javaling.model.Exercise;
import dev.javaling.model.ExerciseLoader;
import dev.javaling.verify.Verifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** Javalings 命令列入口。 */
public final class Javalings {

    /** watch 模式輪詢檔案修改時間的間隔。 */
    private static final long WATCH_POLL_MS = 1_000L;

    private Javalings() {
    }

    public static void main(String[] args) {
        List<String> argsList = new ArrayList<>(List.of(args));
        if (argsList.remove("--no-color")) {
            Console.enableColor(false);
        }

        String dirArg = null;
        int dirIndex = argsList.indexOf("--dir");
        if (dirIndex >= 0 && dirIndex + 1 < argsList.size()) {
            dirArg = argsList.get(dirIndex + 1);
            argsList.remove(dirIndex + 1);
            argsList.remove(dirIndex);
        }

        // --dir 優先；沒給就從目前目錄往上找 exercises/
        Path exercisesDir;
        Path root;
        if (dirArg != null) {
            exercisesDir = Path.of(dirArg).toAbsolutePath().normalize();
            root = exercisesDir.getParent();
        } else {
            root = locateRoot();
            if (root == null) {
                Console.error("找不到練習目錄。請在專案根目錄執行，或用 --dir 指定路徑。");
                System.exit(1);
                return;
            }
            exercisesDir = root.resolve("exercises");
        }

        List<Exercise> exercises;
        try {
            exercises = ExerciseLoader.load(exercisesDir);
        } catch (IOException e) {
            Console.error(e.getMessage());
            System.exit(1);
            return;
        }
        if (exercises.isEmpty()) {
            Console.error("沒有找到任何練習：" + exercisesDir.toAbsolutePath());
            System.exit(1);
            return;
        }

        String command = argsList.isEmpty() ? "welcome" : argsList.remove(0).toLowerCase(Locale.ROOT);
        AppState state;
        try {
            state = AppState.load(root, exercises);
        } catch (IOException e) {
            Console.error("無法讀取進度檔：" + e.getMessage());
            System.exit(1);
            return;
        }

        try {
            switch (command) {
                case "welcome", "help", "--help", "-h" -> welcome(exercises, state, command);
                case "guide" -> guide(exercises, state);
                case "list", "ls" -> list(exercises, state);
                case "run", "r" -> watch(exercises, state, argsList);
                case "verify", "v" -> {
                    if (!verify(exercises, state, argsList)) {
                        System.exit(1);
                    }
                }
                case "hint", "h" -> hint(exercises, state, argsList);
                case "reset" -> reset(root, exercises, state, argsList);
                case "version", "--version", "-v" -> {
                    Console.print(Constants.NAME + " " + Constants.VERSION);
                }
                default -> {
                    Console.error("未知指令：" + command);
                    Console.println();
                    usage();
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            Console.error("進度檔寫入失敗：" + e.getMessage());
            System.exit(1);
        }
    }

    // ---------------------------------------------------------------- commands

    private static void welcome(List<Exercise> exercises, AppState state, String command) throws IOException {
        if (command.equals("help") || command.equals("--help") || command.equals("-h")) {
            usage();
            return;
        }
        Exercise current = find(exercises, state.current()).orElse(exercises.get(0));

        Console.bold(Constants.NAME + " " + Constants.VERSION);
        Console.dim("透過修正有問題的 Java 程式碼來學習 Java。");
        Console.println();
        Console.step("目前進度: " + progressText(state, exercises));
        Console.println();
        Console.step("目前練習: " + current.title());
        Console.dim("  檔案: " + current.source().toAbsolutePath());
        Console.dim("  指令: run 開始監看（存檔自動檢查）/ hint 看提示 / list 看全部");
        Console.println();
        Console.dim("第一次玩？執行 " + Constants.LAUNCHER + " guide ／ 全部指令：" + Constants.LAUNCHER + " help");
    }

    /** 給初學者的操作說明；檔案路徑套用目前練習，其餘為固定文字。 */
    private static void guide(List<Exercise> exercises, AppState state) {
        Exercise current = find(exercises, state.current()).orElse(exercises.get(0));
        String cmd = Constants.LAUNCHER;

        Console.bold(Constants.NAME + " 新手教學");
        Console.println();
        Console.step("怎麼玩");
        Console.print("  每一題都是一個故意寫壞的小程式，把它改到能編譯、而且通過測試。");
        Console.print("  打開檔案修改 → 存檔 → 自動編譯並測試 → 通過後跳到下一題。");
        Console.println();
        Console.step("你現在要改");
        Console.print("  " + current.source().toAbsolutePath());
        Console.dim("  不要改 " + current.test().getFileName() + "：那是寫好的驗收測試。");
        Console.println();
        Console.step("用 run 監看");
        Console.print("  " + cmd + " run 會一直盯著目前這題：");
        Console.print("  - 每次存檔就清空畫面，顯示最新的檢查結果");
        Console.print("  - 通過後自動跳到下一題，全部完成時自動結束");
        Console.print("  - 測試超過 " + Constants.TEST_TIMEOUT_MS / 1000 + " 秒沒跑完會判定逾時，通常是無窮迴圈");
        Console.print("  - 想離開按 Ctrl+C");
        Console.print("  指定題目：" + cmd + " run 15_recursion（可用 id、名稱或主題關鍵字）");
        Console.println();
        Console.step("卡住了怎麼辦");
        Console.print("  1. 仔細看錯誤訊息裡的檔名與行號");
        Console.print("  2. 執行 " + cmd + " hint 看這題的提示");
        Console.print("  3. 真的不會，再去看 solutions/ 裡的參考解答");
        Console.println();
        Console.step("常見問題");
        Console.print("  沒有顏色？傳統主控台不支援色碼，改用 Windows Terminal 就有。");
        Console.print("  進度存在哪？.javalings/progress.txt，刪掉就重新開始。");
        Console.print("  改壞了想重來？" + cmd + " reset <id> 會還原成原題，舊檔備份在 .javalings/backup/。");
        Console.print("  reset --all 只清除進度，不會還原任何原始碼。");
        Console.println();
        Console.dim("完整指令請執行：" + cmd + " help");
    }

    private static void list(List<Exercise> exercises, AppState state) {
        Table table = new Table("狀態", "練習", "難度", "主題");
        for (Exercise ex : exercises) {
            String status = state.isDone(ex.id())
                    ? Console.green("✓ 完成")
                    : (ex.id().equals(state.current()) ? Console.yellow("→ 目前") : "  待做");
            table.row(status, ex.title(), ex.difficulty(), String.join(", ", ex.topics()));
        }
        table.print();
        Console.println();
        Console.dim("共 " + exercises.size() + " 題，已完成 " + state.done().size() + " 題。");
    }

    /**
     * 監看目前練習的原始檔，存檔就重新檢查；通過後自動跟到下一題。
     * 全部完成時正常返回（exit code 0），其餘情況持續執行直到 Ctrl+C。
     */
    private static void watch(List<Exercise> exercises, AppState state, List<String> args) throws IOException {
        if (args.isEmpty()) {
            scanProgress(exercises, state);
        }
        Exercise target = resolveOrExit(exercises, state, args);
        boolean clear = true;
        while (true) {
            if (clear) {
                Console.clearScreen();
            }
            FileTime seen = lastModified(target.source());
            if (check(exercises, state, target, true)) {
                if (exercises.stream().allMatch(ex -> state.isDone(ex.id()))) {
                    return;
                }
                // 不清畫面，讓「通過」訊息留在下一題的檢查結果上方
                target = find(exercises, state.current()).orElseThrow();
                Console.println();
                Console.dim("────────────────────────────────────────");
                clear = false;
                continue;
            }
            Console.println();
            Console.dim("監看中：存檔後會自動重新檢查（Ctrl+C 離開）");
            waitForChange(target.source(), seen);
            clear = true;
        }
    }

    /**
     * 依實際檔案內容重建進度：和 .original/ 原題相同的直接視為未完成，
     * 改過的才編譯測試；最後把目前練習設成第一個未完成的題目。
     */
    private static void scanProgress(List<Exercise> exercises, AppState state) throws IOException {
        List<Exercise> ordered = exercises.stream()
                .sorted(Comparator.comparingInt(Exercise::order))
                .toList();
        String classpath = Console.selfClasspathEntry();
        for (int i = 0; i < ordered.size(); i++) {
            Exercise ex = ordered.get(i);
            Console.raw("\r掃描進度中… " + (i + 1) + "/" + ordered.size() + "  ");
            if (isUnmodified(ex)) {
                state.unmarkDone(ex.id());
            } else if (Verifier.verify(ex, classpath).passed()) {
                state.markDone(ex.id());
            } else {
                state.unmarkDone(ex.id());
            }
        }
        Console.raw("\r" + " ".repeat(30) + "\r");
        ordered.stream()
                .filter(ex -> !state.isDone(ex.id()))
                .findFirst()
                .ifPresent(ex -> state.setCurrent(ex.id()));
        state.save();
    }

    /** 練習檔和 .original/ 的原題完全相同；找不到原題時回傳 false，交給實際檢查判斷。 */
    private static boolean isUnmodified(Exercise ex) {
        Path original = ex.dir().resolve(".original").resolve(ex.sourceFileName() + ".orig");
        try {
            return Files.isRegularFile(original) && Files.isRegularFile(ex.source())
                    && Files.mismatch(ex.source(), original) == -1;
        } catch (IOException e) {
            return false;
        }
    }

    /** 一次性檢查，回傳 true 代表這題通過。給 CI 與 selftest 用。 */
    private static boolean verify(List<Exercise> exercises, AppState state, List<String> args) throws IOException {
        return check(exercises, state, resolveOrExit(exercises, state, args), false);
    }

    /** 編譯並執行一題的測試，通過就推進到下一題。回傳 true 代表這題通過。 */
    private static boolean check(List<Exercise> exercises, AppState state, Exercise target, boolean interactive)
            throws IOException {
        state.setCurrent(target.id());
        state.save();

        if (interactive) {
            Console.step("檢查中: " + target.title());
            Console.dim("  " + target.source().toAbsolutePath());
        }

        Verifier.Result result = Verifier.verify(target, Console.selfClasspathEntry());

        switch (result.status()) {
            case PASSED -> {
                Console.success("✓ " + target.title() + " 通過！");
                if (!result.testOutput().isEmpty()) {
                    printBlock("輸出", result.testOutput());
                }
                if (state.markDone(target.id())) {
                    Console.success("  恭喜完成第 " + state.done().size() + " 題！");
                }
                Optional<Exercise> next = nextPending(exercises, state, target);
                if (next.isPresent()) {
                    state.setCurrent(next.get().id());
                    Console.step("  下一題: " + next.get().title());
                    Console.dim("  " + next.get().source().toAbsolutePath());
                } else {
                    Console.success("  全部練習都完成了，去寫自己的 Java 吧！");
                }
                state.save();
                return true;
            }
            case COMPILE_ERROR -> {
                Console.error("✗ 編譯失敗，程式還不能跑。");
                printBlock("編譯錯誤", String.join(System.lineSeparator(), result.errors()));
                Console.dim("提示：閱讀錯誤訊息中的檔名與行號。");
            }
            case TEST_FAILED -> {
                Console.error("✗ 可以編譯，但測試沒通過。");
                if (!result.testOutput().isEmpty()) {
                    printBlock("測試輸出", result.testOutput());
                }
                Console.dim("提示：測試已經寫好了，去調整 " + target.sourceFileName()
                        + " 讓輸出符合預期。");
            }
            case INTERNAL_ERROR -> {
                Console.error("✗ " + result.message());
                if (!result.testOutput().isEmpty()) {
                    printBlock("輸出", result.testOutput());
                }
            }
        }
        return false;
    }

    private static void hint(List<Exercise> exercises, AppState state, List<String> args) {
        Exercise target = resolve(exercises, state, args);
        if (target == null) {
            Console.error("找不到練習：" + args);
            System.exit(1);
            return;
        }
        Console.step("提示 — " + target.title());
        if (target.hint().isBlank()) {
            Console.dim("（這題沒有額外提示）");
            return;
        }
        Console.println();
        for (String line : target.hint().split("\\R")) {
            Console.print("  " + line);
        }
        Console.println();
        Console.dim("卡住了就執行 " + Constants.LAUNCHER + " reset " + target.id() + " 拿回原始碼。");
    }

    /**
     * reset &lt;id&gt; 會把原始碼還原成題目資料夾 .original/ 裡的原題（舊檔先備份）；
     * reset --all 只清進度，不碰原始碼，避免一次蓋掉所有作業。
     */
    private static void reset(Path root, List<Exercise> exercises, AppState state, List<String> args)
            throws IOException {
        if (!args.isEmpty() && (args.get(0).equals("--all") || args.get(0).equals("-a"))) {
            int cleared = state.done().size();
            state.resetAll(exercises);
            state.save();
            Console.success("已清除 " + cleared + " 題的進度，回到起點。");
            Console.dim("原始碼沒有被還原；要還原某一題請執行 " + Constants.LAUNCHER + " reset <id>。");
            return;
        }
        Exercise target = resolve(exercises, state, args);
        if (target == null) {
            Console.error("找不到練習：" + args);
            System.exit(1);
            return;
        }
        state.unmarkDone(target.id());
        state.setCurrent(target.id());
        state.save();
        Console.success("已將 " + target.title() + " 標記為未完成。");
        restoreSource(root, target);
    }

    /**
     * 用題目資料夾裡 .original/&lt;檔名&gt;.orig 的原題覆蓋練習檔；覆蓋前把目前的檔案備份到 .javalings/backup/&lt;id&gt;/。
     * 原題用 .orig 副檔名，避免被 loader 或編輯器當成第二個 Java 原始檔。
     */
    private static void restoreSource(Path root, Exercise target) throws IOException {
        String fileName = target.sourceFileName();
        Path original = target.dir().resolve(".original").resolve(fileName + ".orig");
        if (!Files.isRegularFile(original)) {
            Console.warn("找不到原題 " + original + "，原始碼沒有還原。");
            return;
        }
        Path source = target.source();
        if (Files.isRegularFile(source) && Files.mismatch(source, original) == -1) {
            Console.dim(fileName + " 本來就是原題，不需要還原。");
            return;
        }
        if (Files.isRegularFile(source)) {
            Path backup = root.resolve(".javalings").resolve("backup").resolve(target.id()).resolve(fileName);
            Files.createDirectories(backup.getParent());
            Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
            Console.dim("你改過的版本已備份到 " + backup);
        }
        Files.copy(original, source, StandardCopyOption.REPLACE_EXISTING);
        Console.success("已將 " + fileName + " 還原成原題。");
    }

    // ---------------------------------------------------------------- helpers

    private static Exercise resolveOrExit(List<Exercise> exercises, AppState state, List<String> args) {
        Exercise target = resolve(exercises, state, args);
        if (target == null) {
            Console.error("找不到練習：" + args);
            System.exit(1);
        }
        return target;
    }

    /** 每秒輪詢一次修改時間，直到檔案和 {@code seen} 不同為止。 */
    private static void waitForChange(Path file, FileTime seen) {
        while (true) {
            try {
                Thread.sleep(WATCH_POLL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            FileTime now = lastModified(file);
            // 編輯器存檔時檔案可能短暫不存在（now == null），等它回來再說
            if (now != null && !now.equals(seen)) {
                return;
            }
        }
    }

    private static FileTime lastModified(Path file) {
        try {
            return Files.getLastModifiedTime(file);
        } catch (IOException e) {
            return null;
        }
    }

    private static Exercise resolve(List<Exercise> exercises, AppState state, List<String> args) {
        if (args.isEmpty()) {
            return find(exercises, state.current()).orElse(exercises.get(0));
        }
        String query = args.get(0);
        return find(exercises, query).orElse(null);
    }

    private static Optional<Exercise> find(List<Exercise> exercises, String query) {
        if (query == null) {
            return Optional.empty();
        }
        String q = query.toLowerCase(Locale.ROOT);
        for (Exercise ex : exercises) {
            if (ex.id().equalsIgnoreCase(query) || ex.id().equalsIgnoreCase(q)) {
                return Optional.of(ex);
            }
        }
        for (Exercise ex : exercises) {
            if (ex.id().toLowerCase(Locale.ROOT).startsWith(q)) {
                return Optional.of(ex);
            }
        }
        for (Exercise ex : exercises) {
            if (ex.name().toLowerCase(Locale.ROOT).contains(q)
                    || ex.topics().stream().anyMatch(t -> t.toLowerCase(Locale.ROOT).contains(q))) {
                return Optional.of(ex);
            }
        }
        return Optional.empty();
    }

    /** 從 {@code from} 往後找第一個未完成的練習，找不到再從頭繞回來。 */
    private static Optional<Exercise> nextPending(List<Exercise> exercises, AppState state, Exercise from) {
        List<Exercise> ordered = exercises.stream()
                .sorted(Comparator.comparingInt(Exercise::order))
                .toList();
        int start = ordered.indexOf(from);
        for (int i = 1; i <= ordered.size(); i++) {
            Exercise ex = ordered.get(Math.floorMod(start + i, ordered.size()));
            if (!state.isDone(ex.id())) {
                return Optional.of(ex);
            }
        }
        return Optional.empty();
    }

    private static void printBlock(String title, String body) {
        Console.println();
        Console.bold(title + ":");
        for (String line : body.split("\\R")) {
            Console.raw("  " + line + System.lineSeparator());
        }
    }

    private static String progressText(AppState state, List<Exercise> exercises) {
        return state.done().size() + "/" + exercises.size() + " 完成";
    }

    private static Path locateRoot() {
        Path cwd = Path.of("").toAbsolutePath();
        Path p = cwd;
        while (p != null) {
            if (Files.isDirectory(p.resolve("exercises"))) {
                return p;
            }
            p = p.getParent();
        }
        return Files.isDirectory(cwd.resolve("exercises")) ? cwd : null;
    }

    private static void usage() {
        Console.bold("用法: " + Constants.LAUNCHER + " [指令] [練習 id]");
        Console.println();
        Console.step("  run [id]          監看練習檔，存檔就自動檢查，通過後跟到下一題");
        Console.step("  verify [id]       只檢查一次並回傳 exit code（適合 CI）");
        Console.step("  list              列出所有練習與進度");
        Console.step("  hint [id]         顯示提示");
        Console.step("  reset [id]        將練習標記為未完成，並把原始碼還原成原題");
        Console.step("  reset --all       清除所有進度（不還原原始碼）");
        Console.step("  guide             新手教學：怎麼玩、卡住怎麼辦");
        Console.step("  help              顯示本說明");
        Console.step("  version           顯示版本");
        Console.println();
        Console.dim("選項: --no-color 關閉色彩、--dir <路徑> 指定練習目錄");
    }
}
