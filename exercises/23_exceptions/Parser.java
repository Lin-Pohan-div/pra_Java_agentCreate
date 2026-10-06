// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.util.regex.Pattern;

public class Parser {

    private static final Pattern NUMBER = Pattern.compile("-?\\d+");

    public static int parse(String text) {
        // TODO: text 不是整數時，丟出 IllegalArgumentException，
        //       而且訊息必須包含「不是數字」
        return Integer.parseInt(text);
    }

    public static int parseOrDefault(String text, int fallback) {
        // TODO: 呼叫 parse()，失敗時捕捉例外並回傳 fallback
        return Integer.parseInt(text);
    }
}
