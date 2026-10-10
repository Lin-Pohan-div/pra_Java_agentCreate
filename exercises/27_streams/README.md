# 27 Stream API

> 難度：★★★★　主題：Stream、Collectors、partitioningBy

## 學習目標

- 理解 Stream 的三段式結構：來源 → 中間操作 → 終端操作
- 會使用 `filter`、`map`、`collect`
- 會使用 `Collectors.toList()`、`joining()`、`partitioningBy()`、`counting()`
- 能精確寫出 lambda 條件，分清楚 `>` 和 `>=` 這類邊界

## 觀念講解

### 三段式

```java
List<String> result = words.stream()        // 1. 來源
        .filter(w -> w.length() > 3)        // 2. 中間操作：可以串很多個
        .map(String::toLowerCase)
        .collect(Collectors.toList());      // 3. 終端操作：產生結果
```

Stream 不會改動原本的集合，而是產生新的結果。中間操作是**惰性**的，直到終端操作出現才會真正執行。

### filter：保留符合條件的元素

`filter` 接受一個 `Predicate<T>`，也就是**回傳 `boolean`** 的 lambda：

```java
prices.stream().filter(p -> p < 100)     // 保留小於 100 的
```

把規格的文字精確翻譯成比較運算子：「超過」「大於」是 `>`，「至少」「大於等於」是 `>=`。拿剛好等於門檻的值去驗算一次。

### map：把每個元素轉換

```java
names.stream().map(n -> n.length())      // String → Integer
names.stream().map(String::trim)         // 方法參考，等同 n -> n.trim()
```

### 常用 Collectors

```java
// 收集成 List
.collect(Collectors.toList())

// 用分隔符號串成一個字串
Stream.of("x", "y", "z").collect(Collectors.joining(" | "));   // "x | y | z"

// 依條件分成 true / false 兩組
Map<Boolean, List<Integer>> parts = ages.stream()
        .collect(Collectors.partitioningBy(a -> a >= 65));
// {false=[...未滿 65...], true=[...65 以上...]}

// 分組後不要清單，只要數量：加上第二個參數（下游收集器）
Map<Boolean, Long> counts = ages.stream()
        .collect(Collectors.partitioningBy(a -> a >= 65, Collectors.counting()));
```

`partitioningBy` 的第一個參數**必須是回傳 boolean 的條件**。傳進一個回傳數字的 lambda，型別就不對，編譯器會報錯。

`partitioningBy` 的結果**一定同時有 `true` 和 `false` 兩個 key**，即使某一組是空的。

### 方法參考

| lambda | 方法參考 |
|---|---|
| `s -> s.toUpperCase()` | `String::toUpperCase` |
| `x -> System.out.println(x)` | `System.out::println` |
| `o -> o.total()` | `Order::total` |

## 常見錯誤

- 把「嚴格大於」寫成 `>=`，或反過來
- 傳給 `filter` 或 `partitioningBy` 的 lambda 沒有回傳 boolean
- 只需要數量，卻忘了加 `Collectors.counting()`
- 以為 `stream()` 操作會修改原本的清單

## 動手做

1. 打開 `StudentStats.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 27_streams`（Linux / macOS：`./javalings.sh run 27_streams`）。
3. 卡住時執行 `.\javalings.cmd hint 27_streams`。

## 延伸（選讀）

- Java 16 起可以直接寫 `.toList()`，取代 `.collect(Collectors.toList())`（回傳的是不可修改的清單）。
- `Collectors.groupingBy(...)` 可以依任意 key 分組，`partitioningBy` 是它只有兩組的特例。
- `IntStream.rangeClosed(1, 10).sum()` 可以處理數值範圍。
