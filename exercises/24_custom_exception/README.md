# 24 自訂例外類別

> 難度：★★★　主題：自訂例外、繼承、例外處理

## 學習目標

- 會繼承 `RuntimeException`，建立有意義的自訂例外
- 分得清 checked 和 unchecked 例外
- 會在商業規則被違反時丟出自訂例外，並在訊息中附上關鍵資訊
- 注意邊界條件：「剛好等於」算不算違反規則

## 觀念講解

### 為什麼要自訂例外

`IllegalArgumentException` 和 `IllegalStateException` 都很通用。自訂例外能讓錯誤的**種類**一目了然，呼叫者也能只捕捉他關心的那一種：

```java
class SeatUnavailableException extends RuntimeException {

    public SeatUnavailableException(String seat) {
        super("座位已被預訂：" + seat);     // 交給父類別保存訊息
    }
}
```

```java
try {
    theater.book("A12");
} catch (SeatUnavailableException e) {
    System.out.println(e.getMessage());   // "座位已被預訂：A12"
}
```

### checked vs. unchecked

| | checked | unchecked |
|---|---|---|
| 繼承自 | `Exception`（但不是 `RuntimeException`） | `RuntimeException` |
| 編譯器要求 | 必須 `throws` 宣告，或用 `try`/`catch` 處理 | 不強制 |
| 典型用途 | 外部世界的問題，例如檔案或網路（`IOException`） | 程式邏輯或規則被違反 |

商業規則的違反（例如餘額不足、座位已滿）通常設計成 unchecked。

### 在規則被違反時丟出

```java
public void book(String seat) {
    if (booked.contains(seat)) {
        throw new SeatUnavailableException(seat);
    }
    booked.add(seat);
}
```

結構是：**先檢查規則 → 違反就 throw → 沒違反才改變狀態**。順序很重要：先改狀態再檢查，物件就會留在錯誤的狀態裡。

### 邊界條件

寫比較式時，把「剛好在邊界上」的情況拿出來單獨想。例如容量 100 人的場地，第 100 位客人能不能進場？規格說「不足」才拒絕時，「剛好夠」就應該成功。這是 `>` 和 `>=` 的差別。

### 程式裡的佔位例外

尚未實作的方法常會先寫 `throw new UnsupportedOperationException("尚未實作")` 或類似的程式當佔位。實作時要記得**把它拿掉**，換成真正的邏輯。

## 常見錯誤

- 邊界條件寫錯，「剛好等於」被誤判成不足
- 先修改狀態才檢查規則
- 丟出錯誤的例外型別，或例外訊息少了測試要求的資訊
- 佔位用的 `throw` 沒有移除

## 動手做

1. 打開 `BankAccount.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 24_custom_exception`（Linux / macOS：`./javalings.sh run 24_custom_exception`）。
3. 卡住時執行 `.\javalings.cmd hint 24_custom_exception`。

## 延伸（選讀）

- 自訂例外可以帶欄位（例如請求的金額），讓 `catch` 的程式取得結構化資訊，而不只是一段文字。
- 包裝底層例外時，把原本的例外當作 cause 傳進去：`super("讀取設定失敗", e)`，保留完整的錯誤鏈。
