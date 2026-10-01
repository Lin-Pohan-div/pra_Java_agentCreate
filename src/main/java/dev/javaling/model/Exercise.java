package dev.javaling.model;

import java.nio.file.Path;
import java.util.List;

/**
 * 一個練習。
 *
 * @param id       目錄名，例如 {@code 01_hello}
 * @param name     顯示名稱
 * @param dir      練習根目錄
 * @param source   學員要修的檔案
 * @param test     驗收測試檔案（不需修改）
 * @param order    排序用的整數前綴
 * @param difficulty 難度
 * @param topics   主題標籤
 * @param hint     提示（多行）
 */
public record Exercise(
        String id,
        String name,
        Path dir,
        Path source,
        Path test,
        int order,
        String difficulty,
        List<String> topics,
        String hint) {

    public String title() {
        return id + " — " + name;
    }

    public String sourceFileName() {
        return source.getFileName().toString();
    }

    public String testClassName() {
        return test.getFileName().toString().replaceFirst("\\.java$", "");
    }

    /** 練習中要修正的原始碼類別名。 */
    public String solutionClassName() {
        return source.getFileName().toString().replaceFirst("\\.java$", "");
    }
}
