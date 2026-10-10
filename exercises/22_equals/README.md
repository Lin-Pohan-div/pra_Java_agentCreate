# 22 equals 與 hashCode

> 難度：★★★　主題：equals、hashCode、instanceof

## 學習目標

- 理解 `==`（同一個物件）和 `equals`（內容相等）的差別
- 會正確覆寫 `equals`：檢查型別，再比較所有決定「相等」的欄位
- 理解 `hashCode` 的契約：兩個物件 `equals` 為真時，`hashCode` 必須相同
- 會用 pattern matching `instanceof` 同時做型別檢查和轉型

## 觀念講解

### 預設的 equals

`Object` 的 `equals` 預設就是 `==`，只有同一個物件才相等。對「值物件」（例如座標、日期、金額）來說，我們通常希望**內容相同就相等**，所以要自己覆寫。

### 覆寫 equals 的標準寫法

```java
public class Point {
    private final int x;
    private final int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;                        // 同一個物件，一定相等
        }
        if (!(o instanceof Point other)) {
            return false;                       // 型別不同（或 o 是 null）
        }
        return x == other.x && y == other.y;    // 比較「所有」關鍵欄位
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);              // 用「同一組」欄位
    }
}
```

- 參數型別必須是 `Object`，寫成 `equals(Point o)` 是**重載**，不是覆寫
- `o instanceof Point other` 檢查成功後，`other` 就是轉型好的 `Point`
- 基本型別欄位用 `==` 比較；物件欄位（例如 `String`）用 `equals` 或 `Objects.equals`

### 哪些欄位要比較？

問自己：**兩個物件什麼時候算「同一個值」？** 只要某個欄位不同，就應該被視為不同，這個欄位就必須放進 `equals`。

### hashCode 契約

`HashMap` 和 `HashSet` 會先用 `hashCode` 找到「格子」，再用 `equals` 比對。如果兩個相等的物件 `hashCode` 不同，它們就會被放進不同的格子，`contains` 和 `get` 都會找不到。

規則：

1. `a.equals(b)` 為 true → `a.hashCode() == b.hashCode()` **必須**成立
2. 反過來不一定：`hashCode` 相同的物件不一定相等（碰撞是允許的）

最簡單的做法是：`equals` 比較哪些欄位，`hashCode` 就用 `Objects.hash(...)` 包同樣那些欄位。

## 常見錯誤

- `equals` 只比較了部分欄位
- `hashCode` 用到的欄位和 `equals` 不一致
- 只覆寫了 `equals`，沒有覆寫 `hashCode`
- 參數型別寫成自己的類別，結果變成重載
- `return` 運算式寫到一半漏了分號

## 動手做

1. 打開 `Money.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 22_equals`（Linux / macOS：`./javalings.sh run 22_equals`）。
3. 卡住時執行 `.\javalings.cmd hint 22_equals`。

## 延伸（選讀）

- `java.util.Objects` 提供 `Objects.equals(a, b)`（能安全處理 null）和 `Objects.hash(...)`。
- 第 30 題的 `record` 會自動產生正確的 `equals` 和 `hashCode`。
- 拿可變欄位計算 `hashCode` 很危險：物件放進 `HashSet` 後再改動欄位，就可能再也找不到它。
