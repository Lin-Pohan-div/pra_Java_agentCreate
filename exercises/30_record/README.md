# 30 綜合題：record 訂單

> 難度：★★★★★　主題：record、Stream、例外、綜合

## 學習目標

- 會用 `record` 宣告不可變的資料類別，並理解它自動產生哪些東西
- 會在 record 裡加入自訂方法
- 綜合運用 Stream（第 27 題）、Optional（第 28 題）和例外（第 23、24 題）
- 能處理空集合這種邊界情況

## 觀念講解

### record

```java
public record Point(int x, int y) { }
```

這一行等於幫你寫好了：

- `private final int x;`、`private final int y;`
- 建構式 `Point(int x, int y)`
- 存取方法 `x()`、`y()`（**沒有** `get` 前綴）
- 依照所有欄位實作的 `equals`、`hashCode`（對照第 22 題）
- `toString()`，輸出 `Point[x=1, y=2]`

record 適合「只是攜帶資料」的物件。

### 加入方法

```java
public record Item(String name, double weightKg, int count) {

    public double totalWeight() {          // 實例方法：直接用欄位名稱
        return weightKg * count;
    }

    public static double totalOf(List<Item> items) {   // static 方法
        ...
    }
}
```

### 用 Stream 做彙總

數值加總最方便的是轉成基本型別的 Stream：

```java
double sum = items.stream()
        .mapToDouble(Item::totalWeight)     // Stream<Item> → DoubleStream
        .sum();                             // 空的 Stream 加總是 0.0
```

### 找最大值與空集合

```java
Optional<Item> heaviest = items.stream()
        .max(Comparator.comparingDouble(Item::totalWeight));
```

`max` 回傳 `Optional`，因為空集合沒有最大值。規格要求「空集合時丟出指定的例外」時，可以使用第 28 題學過的方法：

```java
return heaviest.orElseThrow(() -> new IllegalStateException("沒有任何品項"));
```

範例的例外型別和訊息只是示範，**以本題的 TODO 規格為準**。

### 處理順序建議

綜合題可以拆成小步驟，一次只通過一部分：

1. 先實作最簡單、不依賴其他方法的那一個
2. 再實作依賴它的彙總方法
3. 最後處理需要 Optional 和例外的方法

每完成一個就存檔，看看 Javalings 的輸出，確認新的測試通過。

## 常見錯誤

- 用 `getQuantity()` 這種 getter 名稱存取 record 欄位，正確寫法是 `quantity()`
- 佔位用的回傳值或 `throw` 沒有換掉
- 空清單時直接呼叫 `Optional.get()`，丟出了錯誤的例外
- `Comparator` 比較的欄位跟規格不同，例如比單價而不是總金額

## 動手做

1. 打開 `Order.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 30_record`（Linux / macOS：`./javalings.sh run 30_record`）。
3. 卡住時執行 `.\javalings.cmd hint 30_record`。

## 延伸（選讀）

- **緊湊建構式**可以在 record 裡驗證參數：

  ```java
  public record Item(String name, double weightKg, int count) {
      public Item {
          if (count < 0) throw new IllegalArgumentException("數量不能是負的");
      }
  }
  ```

- record 不能繼承其他類別，但可以實作介面。
- 恭喜完成全部 30 題！回頭重做幾題，試著用不同寫法解，例如把迴圈改成 Stream，會是很好的複習。
