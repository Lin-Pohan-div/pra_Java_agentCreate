# 11 ArrayList

> 難度：★★　主題：ArrayList、泛型、集合

## 學習目標

- 會建立 `ArrayList`，並使用 `add`、`get`、`size` 等方法
- 理解 `ArrayList<Integer>` 中，泛型參數只能放參考型別
- 會走訪清單並產生新的清單（轉換、篩選）
- 知道「回傳新清單」和「回傳原本那份」的差別

## 觀念講解

### 陣列 vs. ArrayList

| | 陣列 `int[]` | `ArrayList<Integer>` |
|---|---|---|
| 容量 | 建立後固定 | 可以一直 `add` |
| 讀取 | `a[i]` | `list.get(i)` |
| 長度 | `a.length` | `list.size()` |
| 元素型別 | 可以是基本型別 | 只能是參考型別（`Integer`，不能用 `int`） |

### 基本操作

```java
import java.util.ArrayList;

ArrayList<String> fruits = new ArrayList<>();
fruits.add("apple");
fruits.add("banana");
fruits.get(0);        // "apple"
fruits.size();        // 2
fruits.remove(0);     // 移除索引 0
fruits.contains("banana");  // true
```

`new ArrayList<>()` 的 `<>` 稱為 diamond，編譯器會根據左邊的型別推斷內容。

### 自動裝箱

`int` 和 `Integer` 之間會自動轉換，所以可以直接這樣寫：

```java
ArrayList<Integer> nums = new ArrayList<>();
nums.add(5);              // int → Integer
int first = nums.get(0);  // Integer → int
for (int n : nums) { ... }
```

### 轉換與篩選

這兩種是最常見的清單處理模式，都是「建立新清單 → 走訪 → 決定放什麼」：

```java
// 轉換：每個元素做同樣的處理
ArrayList<String> upper = new ArrayList<>();
for (String s : fruits) {
    upper.add(s.toUpperCase());
}

// 篩選：只放符合條件的元素
ArrayList<String> longNames = new ArrayList<>();
for (String s : fruits) {
    if (s.length() > 5) {
        longNames.add(s);
    }
}
```

### 參考的陷阱

物件變數存的是**參考**。把參數原封不動回傳，呼叫端拿到的就是**同一份**清單：之後任何一方修改，另一方都會看到。要產生「結果」時，通常應該建立新的清單。

## 常見錯誤

- 寫成 `ArrayList<int>`
- 想篩選或轉換，卻直接回傳原本的清單
- 用 `list.length` 或 `list[i]`，這是陣列的寫法
- 迴圈本體的陳述句漏了分號

## 動手做

1. 打開 `NumberList.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 11_arraylist`（Linux / macOS：`./javalings.sh run 11_arraylist`）。
3. 卡住時執行 `.\javalings.cmd hint 11_arraylist`。

## 延伸（選讀）

- 宣告時常寫成介面型別：`List<Integer> nums = new ArrayList<>();`，日後要換實作比較方便。
- `List.of(1, 2, 3)` 可以快速建立**不可修改**的清單。
- 對 `ArrayList<Integer>` 呼叫 `remove(1)` 會移除**索引 1**，而不是值 1；要移除值，請用 `remove(Integer.valueOf(1))`。
