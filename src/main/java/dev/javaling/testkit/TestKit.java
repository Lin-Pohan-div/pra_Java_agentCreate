package dev.javaling.testkit;

import dev.javaling.Constants;

import java.util.Arrays;
import java.util.Objects;

/**
 * 練習驗收測試用的極簡斷言工具（刻意不依賴 JUnit）。
 *
 * <p>用法：在測試類別的 {@code main} 中呼叫斷言，全部通過後呼叫 {@link #done()}。
 * 任何斷言失敗會拋出 {@link AssertionError}，Javalings 便會把該練習標為未完成。
 */
public final class TestKit {

    private static int checks;

    private TestKit() {
    }

    /** 全部斷言通過後呼叫：印出通過標記。 */
    public static void done() {
        System.out.println(Constants.PASS_MARKER);
        System.out.flush();
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public static void expectTrue(String label, boolean value) {
        checks++;
        if (!value) {
            throw error(label, "true", "false");
        }
    }

    public static void expectFalse(String label, boolean value) {
        checks++;
        if (value) {
            throw error(label, "false", "true");
        }
    }

    public static void expectEquals(String label, Object expected, Object actual) {
        checks++;
        if (!deepEquals(expected, actual)) {
            throw error(label, render(expected), render(actual));
        }
    }

    /** 陣列要用逐元素比較，{@code Objects.equals} 對陣列只會比參考。 */
    private static boolean deepEquals(Object a, Object b) {
        if (a instanceof int[] x && b instanceof int[] y) {
            return Arrays.equals(x, y);
        }
        if (a instanceof long[] x && b instanceof long[] y) {
            return Arrays.equals(x, y);
        }
        if (a instanceof double[] x && b instanceof double[] y) {
            return Arrays.equals(x, y);
        }
        if (a instanceof char[] x && b instanceof char[] y) {
            return Arrays.equals(x, y);
        }
        if (a instanceof boolean[] x && b instanceof boolean[] y) {
            return Arrays.equals(x, y);
        }
        if (a instanceof Object[] x && b instanceof Object[] y) {
            return Arrays.deepEquals(x, y);
        }
        return Objects.equals(a, b);
    }

    public static void expectEquals(Object expected, Object actual) {
        expectEquals("結果", expected, actual);
    }

    public static void expectClose(String label, double expected, double actual) {
        checks++;
        if (Math.abs(expected - actual) > 1e-9) {
            throw error(label, String.valueOf(expected), String.valueOf(actual));
        }
    }

    /** 斷言主程式會丟出指定類型的例外。 */
    public static void expectThrows(String label, Class<? extends Throwable> type, Runnable body) {
        checks++;
        try {
            body.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw error(label, type.getSimpleName(), t.getClass().getSimpleName() + ": " + t.getMessage());
        }
        throw error(label, type.getSimpleName(), "（沒有丟出例外）");
    }

    /** 斷言主程式會丟出指定類型、且訊息包含指定文字的例外。 */
    public static void expectThrowsMessage(
            String label, Class<? extends Throwable> type, String messagePart, Runnable body) {
        checks++;
        try {
            body.run();
        } catch (Throwable t) {
            if (!type.isInstance(t)) {
                throw error(label, type.getSimpleName(),
                        t.getClass().getSimpleName() + ": " + t.getMessage());
            }
            String actual = t.getMessage() == null ? "null" : t.getMessage();
            if (!actual.contains(messagePart)) {
                throw error(label, type.getSimpleName() + " 且訊息包含 \"" + messagePart + "\"",
                        type.getSimpleName() + ": " + actual);
            }
            return;
        }
        throw error(label, type.getSimpleName() + " 且訊息包含 \"" + messagePart + "\"", "（沒有丟出例外）");
    }

    public static void expectNotNull(String label, Object value) {
        checks++;
        if (value == null) {
            throw error(label, "非 null", "null");
        }
    }

    private static AssertionError error(String label, String expected, String actual) {
        return new AssertionError(
                label + "\n  期望: " + expected + "\n  實際: " + actual);
    }

    /** 把值轉成好讀的字串，陣列會展開顯示。 */
    private static String render(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof int[] v) {
            return Arrays.toString(v);
        }
        if (value instanceof long[] v) {
            return Arrays.toString(v);
        }
        if (value instanceof double[] v) {
            return Arrays.toString(v);
        }
        if (value instanceof char[] v) {
            return Arrays.toString(v);
        }
        if (value instanceof boolean[] v) {
            return Arrays.toString(v);
        }
        if (value instanceof Object[] v) {
            return Arrays.deepToString(v);
        }
        return String.valueOf(value);
    }
}
