# 21 列舉 enum

> 難度：★★★　主題：enum、switch、values

## 學習目標

- 會宣告 enum，並理解它是「固定的一組常數」
- 會在 enum 裡加入方法
- 會使用 switch 運算式（`->` 語法）並回傳值
- 會用 `values()` 走訪所有常數，找出符合條件的那一個

## 觀念講解

### enum 是什麼

當一個值只可能是少數幾種固定選項時，用 enum 比用字串或數字安全：

```java
public enum Size {
    SMALL, MEDIUM, LARGE;
}

Size s = Size.MEDIUM;
s.name();        // "MEDIUM"
s.ordinal();     // 1（宣告順序，從 0 開始）
Size.values();   // [SMALL, MEDIUM, LARGE]
```

拼錯 `Size.MEDUIM` 會在編譯時就被抓到，這是字串做不到的。

### enum 可以有方法

在常數列表後面加分號，就可以寫方法。方法裡的 `this` 就是目前這個常數：

```java
public enum Size {
    SMALL, MEDIUM, LARGE;

    public int ml() {
        return switch (this) {
            case SMALL -> 350;
            case MEDIUM -> 500;
            case LARGE -> 700;
        };
    }
}

Size.LARGE.ml();   // 700
```

### switch 運算式

和第 07 題的傳統 switch 敘述相比：

| | switch 敘述 | switch 運算式 |
|---|---|---|
| 分支符號 | `case X:` | `case X -> 值;` |
| fall-through | 有 | 沒有 |
| 產生值 | 不會 | 會，可以直接 `return switch ...` |
| 窮舉檢查 | 沒有 | 對 enum 必須涵蓋全部常數（或寫 `default`） |

每個分支都必須是完整的 `case 常數 -> 值;`。整個 switch 運算式結束時，`}` 後面還要加分號，因為它是 `return` 陳述句的一部分。

### 找出最大的那一個

```java
public static Size biggest() {
    Size best = SMALL;
    for (Size s : values()) {
        if (s.ml() > best.ml()) {     // 比目前的紀錄「更好」才更新
            best = s;
        }
    }
    return best;
}
```

找最大值時，條件應該是「新的比紀錄**大**」。條件方向寫反，就會變成找最小值。

## 常見錯誤

- switch 運算式的某個分支沒寫完整，例如少了 `->` 或值
- 混用 `:` 和 `->` 兩種語法
- 找最大／最小時比較方向寫反
- 拿 `==` 比較 enum 是**正確的**，不必改成 `equals`

## 動手做

1. 打開 `TrafficLight.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 21_enum`（Linux / macOS：`./javalings.sh run 21_enum`）。
3. 卡住時執行 `.\javalings.cmd hint 21_enum`。

## 延伸（選讀）

- enum 可以有欄位和建構式：`SMALL(350), MEDIUM(500), LARGE(700);` 搭配 `private final int ml;`，往往比 switch 更簡潔。
- `Size.valueOf("LARGE")` 可以把字串轉成 enum，字串不符時會丟出 `IllegalArgumentException`。
- `EnumMap` 和 `EnumSet` 是專為 enum 設計的高效集合。
