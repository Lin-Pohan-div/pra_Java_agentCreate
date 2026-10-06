# 25 try-with-resources

> 難度：★★★★　主題：IO、try-with-resources、BufferedReader

## 學習目標

- 理解「資源」（檔案、網路連線等）用完一定要關閉
- 會用 try-with-resources，讓資源自動關閉
- 會用 `BufferedReader` 逐行讀取檔案，直到讀到結尾
- 會用 `throws IOException` 把 checked 例外交給呼叫者處理

## 觀念講解

### 為什麼要關閉資源

開啟的檔案會佔用作業系統的資源。忘了關閉，輕則浪費資源，重則讓檔案被鎖住、無法刪除。更麻煩的是，**中途丟出例外時**，後面的 `close()` 根本不會被執行到。

### 舊寫法：finally

```java
BufferedReader reader = Files.newBufferedReader(path);
try {
    ...
} finally {
    reader.close();   // 不論成功或失敗都會執行
}
```

能用，但很囉嗦；開好幾個資源時，還會變成一層包一層。

### try-with-resources

```java
try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
    return reader.readLine();
}   // 離開這個區塊時（包含例外），reader 會自動 close()
```

- 小括號裡宣告的變數，必須實作 `AutoCloseable` 介面
- 可以用分號隔開，一次宣告多個資源，關閉順序和宣告順序相反

### 逐行讀到結尾

`readLine()` 每次回傳一行（不含換行字元），**讀到檔案結尾時回傳 `null`**。標準寫法是在 `while` 的條件裡同時讀取與判斷：

```java
String line;
while ((line = reader.readLine()) != null) {
    // 處理 line
}
```

拆開來看：`(line = reader.readLine())` 先讀一行並存進 `line`，整個指定運算式的值就是剛讀到的那一行，再拿去和 `null` 比較。**內層括號不能省略**，否則會先做 `!=` 比較。

例如計算「有幾行包含 TODO」：

```java
int todos = 0;
try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
    String line;
    while ((line = reader.readLine()) != null) {
        if (line.contains("TODO")) {
            todos++;
        }
    }
}
return todos;
```

### checked 例外：throws

讀檔的方法會丟出 `IOException`。它是 checked 例外（見第 24 題），所以方法簽章要加上 `throws IOException`，把它交給呼叫者處理。

### 照著範例做

同一個檔案裡，如果已經有一個方法示範了正確的資源處理方式，就先讀懂它，再照同樣的模式寫新方法。這是實務上很重要的習慣：**跟著程式庫既有的寫法走**。

## 常見錯誤

- 開檔後沒有關閉，或只在成功路徑上關閉
- 只讀了一行，沒有用迴圈讀到 `null`
- `while` 條件的括號放錯
- 範例方法本身有語法錯誤，例如漏了分號
- 佔位用的回傳值沒換成真正的計算結果

## 動手做

1. 打開 `LineCounter.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 25_try_with_resources`（Linux / macOS：`./javalings.sh run 25_try_with_resources`）。
3. 卡住時執行 `.\javalings.cmd hint 25_try_with_resources`。

## 延伸（選讀）

- 小檔案可以一行讀完：`Files.readAllLines(path)`、`Files.readString(path)`，或用 `Files.lines(path)` 取得 Stream（Stream 也要放進 try-with-resources）。
- 永遠明確指定字元編碼（例如 `StandardCharsets.UTF_8`），不要依賴作業系統的預設值。
