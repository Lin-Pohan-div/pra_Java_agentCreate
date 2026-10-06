# 12 HashMap 統計字數

> 難度：★★★　主題：HashMap、Map、getOrDefault

## 學習目標

- 理解 `Map` 是「鍵 → 值」的對應表
- 會使用 `put`、`get`、`getOrDefault`、`containsKey`
- 會寫「計數」模式：讀出目前的次數，加一後寫回
- 會在統計前先把資料正規化，例如統一大小寫

## 觀念講解

### Map 的基本操作

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> stock = new HashMap<>();
stock.put("apple", 3);
stock.put("pear", 5);
stock.put("apple", 10);        // 同一個 key 再 put 會覆蓋

stock.get("apple");            // 10
stock.get("kiwi");             // null，沒有這個 key
stock.containsKey("pear");     // true
stock.size();                  // 2
```

每個 key 只會出現一次；value 可以重複。

### getOrDefault

`get` 找不到時會回傳 `null`，拿 `null` 去做加法會丟出 NullPointerException。`getOrDefault` 讓你指定「找不到時的預設值」：

```java
stock.getOrDefault("kiwi", 0);   // 0
```

### 計數模式

統計「每種東西出現幾次」幾乎都是這個寫法：

```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : "banana".toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}
// {a=3, b=1, n=2}
```

### 先正規化再統計

`"Cat"` 和 `"cat"` 是不同的字串，所以也是不同的 key。題目要求「不分大小寫」時，要在**放進 Map 之前**先統一成小寫或大寫。

### 走訪 Map

```java
for (String key : stock.keySet()) { ... }        // 只要 key
for (int qty : stock.values()) { ... }            // 只要 value
for (Map.Entry<String, Integer> e : stock.entrySet()) {
    e.getKey(); e.getValue();                     // 兩個都要
}
```

### 再次提醒：整數除法

兩個 `int` 相除仍然是整數除法（見第 02 題）。要算平均，得先讓其中一邊變成 `double`。

## 常見錯誤

- 用 `get` 取計數，在 key 不存在時得到 `null`
- 忘了把資料正規化，造成同一個字被拆成好幾個 key
- 平均值用 `int / int` 計算，小數被吃掉
- 以為 `HashMap` 會記住插入順序（不會，需要順序請用 `LinkedHashMap`）

## 動手做

1. 打開 `WordCount.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 12_hashmap`（Linux / macOS：`./javalings.sh run 12_hashmap`）。
3. 卡住時執行 `.\javalings.cmd hint 12_hashmap`。

## 延伸（選讀）

- `map.merge(key, 1, Integer::sum)` 是計數模式的一行寫法。
- `TreeMap` 會依照 key 排序。
- 字串的 `split("\\s+")` 用正規表示式切開「一個以上的空白」。
