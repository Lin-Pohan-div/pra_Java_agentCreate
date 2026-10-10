# 28 Optional

> 難度：★★★　主題：Optional、Map、例外

## 學習目標

- 理解 `Optional<T>`：用型別明確表達「可能沒有值」
- 會用 `Optional.ofNullable`、`Optional.empty`、`Optional.of` 建立 Optional
- 會用 `orElse`、`isPresent`、`isEmpty` 等方法安全地取值
- 知道 `get()` 在沒有值時會丟出例外，以及怎麼改成丟出自己的例外

## 觀念講解

### 問題：null 太容易被忽略

```java
String phone = contacts.get("Amy");    // 找不到就是 null
phone.length();                        // NullPointerException！
```

方法簽章寫的是 `String`，看不出它可能回傳 `null`。`Optional<String>` 則在型別上就告訴呼叫者：**這裡可能沒有值，請處理**。

### 建立 Optional

```java
Optional.of("x")            // 一定有值（傳 null 會直接丟例外）
Optional.empty()            // 一定沒有值
Optional.ofNullable(maybe)  // maybe 是 null → empty；否則包起來
```

包裝可能回傳 `null` 的 API（例如 `Map.get`）時，`ofNullable` 是最常用的寫法。

### 取出值

```java
Optional<String> phone = find("Amy");

phone.isPresent()                 // 有值嗎？
phone.isEmpty()                   // 沒值嗎？（Java 11+）
phone.orElse("未提供")             // 沒值時用預設值
phone.orElseGet(() -> lookup())   // 沒值時才計算預設值
phone.map(String::trim)           // 有值時才轉換，結果仍是 Optional
phone.ifPresent(System.out::println);
```

### get() 的陷阱

```java
Optional.empty().get();   // 丟出 NoSuchElementException
```

直接呼叫 `get()` 等於假設「一定有值」，又回到 null 的老問題。規格要求在沒有值時丟出**特定的**例外和訊息，就得自己先檢查：

```java
public String requireEmail(String user) {
    Optional<String> email = findEmail(user);
    if (email.isEmpty()) {
        throw new IllegalStateException("使用者沒有 email：" + user);
    }
    return email.get();
}
```

或用一行寫法：`email.orElseThrow(() -> new IllegalStateException(...))`。

注意上面的範例刻意使用 `IllegalStateException`。實際要丟哪一種例外、訊息要包含什麼，**以本題的規格為準**。

## 常見錯誤

- 沒檢查就呼叫 `get()`
- 丟出的例外型別或訊息內容跟規格不符
- 回傳 `null` 給宣告為 `Optional` 的方法（應該回傳 `Optional.empty()`）
- 值可能是 `null` 時用了 `Optional.of`，結果直接丟出 `NullPointerException`

## 動手做

1. 打開 `Config.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 28_optional`（Linux / macOS：`./javalings.sh run 28_optional`）。
3. 卡住時執行 `.\javalings.cmd hint 28_optional`。

## 延伸（選讀）

- `Optional` 適合當**回傳值**；一般不建議用在欄位或方法參數。
- `Optional<Integer>` 有對應的基本型別版本 `OptionalInt`，Stream 的 `max()`、`average()` 等方法會用到。
- 第 30 題會用到 `Stream.max(...)` 回傳的 `Optional`。
