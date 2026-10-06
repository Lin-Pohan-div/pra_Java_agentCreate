# 23 丟出與捕捉例外

> 難度：★★★　主題：例外、throw、try/catch、Pattern

## 學習目標

- 會用 `throw new XxxException("訊息")` 主動回報錯誤
- 會在處理資料前先驗證輸入，並丟出訊息清楚的例外
- 會用 `try` / `catch` 捕捉例外，並提供替代結果
- 會用正規表示式 `Pattern` 檢查字串格式

## 觀念講解

### 為什麼要丟例外

方法遇到「無法完成任務」的輸入時，與其回傳一個假的值（例如 `-1`），不如明確丟出例外，讓呼叫者知道出錯了：

```java
public static int age(int birthYear, int thisYear) {
    if (birthYear > thisYear) {
        throw new IllegalArgumentException("出生年不能晚於今年：" + birthYear);
    }
    return thisYear - birthYear;
}
```

`IllegalArgumentException` 代表「呼叫者給了不合法的參數」。訊息要能幫助閱讀的人理解問題。

### 先驗證，再處理

如果直接把輸入交給別的函式庫，出錯時拿到的會是**它的**例外和**它的**訊息，通常是英文、而且很技術性。想控制錯誤訊息，就要**自己先檢查**，不合格就丟出自己的例外：

```java
private static final Pattern ZIP = Pattern.compile("\\d{3}(\\d{2})?");

public static String zip(String text) {
    if (!ZIP.matcher(text).matches()) {
        throw new IllegalArgumentException("郵遞區號格式錯誤：" + text);
    }
    return text;
}
```

### 正規表示式速查

| 寫法 | 意思 |
|---|---|
| `\\d` | 一個數字（Java 字串裡反斜線要寫兩次） |
| `+` | 前一個出現一次以上 |
| `?` | 前一個可有可無 |
| `-?\\d+` | 可能帶負號的整數 |

`pattern.matcher(text).matches()` 會檢查**整個字串**是否符合。

### try / catch

```java
public static int ageOrZero(int birthYear, int thisYear) {
    try {
        return age(birthYear, thisYear);       // 可能丟例外的程式
    } catch (IllegalArgumentException e) {
        return 0;                              // 出錯時的替代方案
    }
}
```

- `catch` 只會接住你指定的型別（以及它的子類別）
- 不要用 `catch (Exception e)` 把所有例外都吞掉，這會把真正的 bug 一起藏起來
- 已經有一個會驗證的方法時，就**重用它**，不要另外寫一份邏輯

## 常見錯誤

- 沒有先驗證就直接交給函式庫解析，結果拋出的例外型別或訊息不符合規格
- 例外訊息缺少規格要求的關鍵字
- 「失敗時回傳預設值」的方法沒有用 `try` / `catch`，或沒有呼叫已經寫好的驗證方法
- 正規表示式少寫了反斜線跳脫

## 動手做

1. 打開 `Parser.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 23_exceptions`（Linux / macOS：`./javalings.sh run 23_exceptions`）。
3. 卡住時執行 `.\javalings.cmd hint 23_exceptions`。

## 延伸（選讀）

- `NumberFormatException` 其實是 `IllegalArgumentException` 的子類別，所以 `catch (IllegalArgumentException e)` 也能接住它。
- `finally { ... }` 區塊不論有沒有例外都會執行，常用來釋放資源；第 25 題會介紹更好的寫法。
- 例外分成 checked 和 unchecked 兩類，第 24 題會說明。
