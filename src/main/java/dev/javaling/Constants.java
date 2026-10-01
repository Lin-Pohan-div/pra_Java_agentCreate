package dev.javaling;

/** 跨模組共用的常數。 */
public final class Constants {

    private Constants() {
    }

    public static final String NAME = "Javalings";
    public static final String VERSION = "0.1.0";

    /**
     * 提示訊息中顯示的啟動指令。系統上沒有名為 javalings 的指令，
     * 而 PowerShell 不會從目前目錄找執行檔，所以必須帶上 .\ 前綴（cmd 也接受）。
     */
    public static final String LAUNCHER =
            System.getProperty("os.name", "").toLowerCase().contains("win") ? ".\\javalings.cmd" : "./javalings.sh";

    /** 測試全部通過時由 TestKit 印出的標記。 */
    public static final String PASS_MARKER = "JAVALINGS_TESTS_PASSED";

    /** 測試失敗時由 TestKit 印出的標記前綴。 */
    public static final String FAIL_MARKER = "JAVALINGS_TESTS_FAILED";

    /** 測試執行逾時（毫秒）。 */
    public static final long TEST_TIMEOUT_MS = 10_000L;
}
