package dev.javaling.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;

/** 終端機輸出與輸入的薄包裝：色彩、輸入讀取、自身 classpath 位置。 */
public final class Console {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String DIM = "\u001B[2m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    /**
     * 終端機實際使用的編碼。Windows 中文版主控台預設是 cp950（Big5），
     * 若硬寫 UTF-8 位元組就會變成亂碼，所以交給 JVM 偵測到的編碼決定。
     */
    private static final Charset CHARSET = terminalCharset();
    private static final CharsetEncoder ENCODER = CHARSET.newEncoder();

    /** 目前編碼表示不了的符號（例如 cp950 沒有 ✓ ✗）改用 ASCII 替代。 */
    private static final Map<Character, String> FALLBACKS = Map.of(
            '✓', "v",
            '✗', "x",
            '→', "->",
            '—', "-",
            '─', "-");

    private static final PrintStream OUT =
            new PrintStream(new FileOutputStream(FileDescriptor.out), true, CHARSET);
    private static final BufferedReader IN =
            new BufferedReader(new InputStreamReader(System.in, CHARSET));

    private static boolean color = System.console() != null && !isWindowsWithoutAnsi();

    private Console() {
    }

    /**
     * stdout.encoding（JDK 19+）與 sun.stdout.encoding（JDK 17/18）在接上主控台時會是
     * 主控台字碼頁；被導向檔案或管線時則退回系統原生編碼，與 PowerShell 解讀的一致。
     */
    private static Charset terminalCharset() {
        for (String key : new String[] {"stdout.encoding", "sun.stdout.encoding", "native.encoding"}) {
            String name = System.getProperty(key);
            if (name != null && Charset.isSupported(name)) {
                return Charset.forName(name);
            }
        }
        return Charset.defaultCharset();
    }

    /** 把目前編碼表示不了的字元換成替代字串，避免印出問號。 */
    private static String fit(String text) {
        if (ENCODER.canEncode(text)) {
            return text;
        }
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (ENCODER.canEncode(c)) {
                sb.append(c);
            } else {
                sb.append(FALLBACKS.getOrDefault(c, "?"));
            }
        }
        return sb.toString();
    }

    private static boolean isWindowsWithoutAnsi() {
        String os = System.getProperty("os.name", "").toLowerCase();
        return os.contains("win") && System.getenv("WT_SESSION") == null && System.getenv("ConEmuANSI") == null;
    }

    public static void enableColor(boolean enabled) {
        color = enabled;
    }

    public static boolean colorEnabled() {
        return color;
    }

    private static String paint(String code, String text) {
        return color ? code + text + RESET : text;
    }

    public static void print(String text) {
        OUT.println(fit(text));
    }

    public static void println() {
        OUT.println();
    }

    public static void println(String text) {
        OUT.println(fit(text));
    }

    public static void bold(String text) {
        print(paint(BOLD, text));
    }

    public static void dim(String text) {
        print(paint(DIM, text));
    }

    public static void info(String text) {
        print(paint(CYAN, text));
    }

    public static void step(String text) {
        print(paint(BLUE, text));
    }

    public static void success(String text) {
        print(paint(GREEN, text));
    }

    /** 回傳帶綠色的字串（給 Table 等需要字串的情境用）。 */
    public static String green(String text) {
        return paint(GREEN, text);
    }

    /** 回傳帶黃色的字串。 */
    public static String yellow(String text) {
        return paint(YELLOW, text);
    }

    public static void warn(String text) {
        print(paint(YELLOW, text));
    }

    public static void error(String text) {
        print(paint(RED, text));
    }

    public static void raw(String text) {
        OUT.print(fit(text));
        OUT.flush();
    }

    /**
     * 清除終端機畫面。與色彩判斷分開：VS Code 終端機沒有 WT_SESSION，會被判成不支援 ANSI，
     * 但 cls 在任何 Windows 主控台都能清，所以 Windows 一律交給 cls。
     */
    public static void clearScreen() {
        if (System.console() == null) {
            return;
        }
        OUT.flush();
        if (System.getProperty("os.name", "").toLowerCase().contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException e) {
                // 清不掉就算了，接著印結果即可
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            OUT.print("\u001B[H\u001B[2J\u001B[3J");
            OUT.flush();
        }
    }

    public static String readLine() {
        try {
            return IN.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /** 取得 Javalings 自身所在的 jar 或 class 目錄，用於編譯/執行練習時的 classpath。 */
    public static String selfClasspathEntry() {
        try {
            File self = new File(Console.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return self.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
