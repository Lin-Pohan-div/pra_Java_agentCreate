package dev.javaling.cli;

import java.util.ArrayList;
import java.util.List;

/** 極簡等寬表格，用於 list 指令。 */
public final class Table {

    private final List<String> headers;
    private final List<List<String>> rows = new ArrayList<>();

    public Table(String... headers) {
        this.headers = List.of(headers);
    }

    public void row(String... cells) {
        if (cells.length != headers.size()) {
            throw new IllegalArgumentException(
                    "欄位數不符：需要 " + headers.size() + " 個，收到 " + cells.length + " 個");
        }
        rows.add(List.of(cells));
    }

    public void print() {
        int columns = headers.size();
        int[] visible = new int[columns];
        for (int i = 0; i < columns; i++) {
            visible[i] = displayWidth(headers.get(i));
        }
        for (List<String> row : rows) {
            for (int i = 0; i < columns; i++) {
                visible[i] = Math.max(visible[i], displayWidth(row.get(i)));
            }
        }

        String divider = "+" + "─".repeat(visible[0] + 2);
        for (int i = 1; i < columns; i++) {
            divider += "+" + "─".repeat(visible[i] + 2);
        }
        divider += "+";

        Console.println(divider);
        StringBuilder head = new StringBuilder();
        for (int i = 0; i < columns; i++) {
            head.append("| ").append(pad(headers.get(i), visible[i])).append(' ');
        }
        Console.print(head + "|");
        Console.println(divider);

        for (List<String> row : rows) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < columns; i++) {
                line.append("| ").append(pad(row.get(i), visible[i])).append(' ');
            }
            Console.print(line + "|");
        }
        Console.println(divider);
    }

    /** 一律靠左對齊：中英混排時右對齊很難閱讀。 */
    private static String pad(String cell, int width) {
        return cell + " ".repeat(Math.max(0, width - displayWidth(cell)));
    }

    /** 計算顯示寬度：略過 ANSI 色彩碼，並把中日韓文字視為兩格。 */
    private static int displayWidth(String text) {
        int width = 0;
        boolean inEscape = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (inEscape) {
                if (c == 'm') {
                    inEscape = false;
                }
                continue;
            }
            if (c == '\u001B') {
                inEscape = true;
                continue;
            }
            width += isWide(c) ? 2 : 1;
        }
        return width;
    }

    private static boolean isWide(char c) {
        return (c >= 0x1100 && c <= 0x115F)
                || (c >= 0x2013 && c <= 0x2014)   // – —
                || (c >= 0x2018 && c <= 0x201F)   // 「」『』…
                || (c >= 0x2E80 && c <= 0xA4CF)
                || (c >= 0xAC00 && c <= 0xD7A3)
                || (c >= 0xF900 && c <= 0xFAFF)
                || (c >= 0xFE30 && c <= 0xFE6F)
                || (c >= 0xFF00 && c <= 0xFF60)
                || (c >= 0xFFE0 && c <= 0xFFE6);
    }
}
