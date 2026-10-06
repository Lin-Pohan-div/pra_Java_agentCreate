# 29 自訂 Iterator

> 難度：★★★★　主題：Iterator、Iterable、泛型

## 學習目標

- 理解 for-each 背後的運作：`Iterable` 產生 `Iterator`，`Iterator` 一個一個交出元素
- 會實作 `hasNext()` 和 `next()`，並遵守迭代器協議
- 會處理「遞增」和「遞減」兩種方向
- 知道 `next()` 在沒有元素時必須丟出 `NoSuchElementException`

## 觀念講解

### for-each 的真面目

```java
for (String s : list) {
    System.out.println(s);
}
```

編譯器會把它改寫成：

```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    System.out.println(s);
}
```

所以只要你的類別實作了 `Iterable<T>`，也就是提供 `iterator()` 方法，就能用在 for-each 裡。

### 迭代器協議

| 方法 | 責任 |
|---|---|
| `hasNext()` | 回答「還有沒有下一個」，**不能改變狀態**，呼叫幾次結果都一樣 |
| `next()` | 交出「目前這一個」，並**前進**到下一個；沒有東西時丟出 `NoSuchElementException` |

### 範例：倒數迭代器

```java
public class Countdown implements Iterable<Integer> {
    private final int from;

    public Countdown(int from) {
        this.from = from;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {          // 匿名內部類別
            private int current = from;

            @Override
            public boolean hasNext() {
                return current >= 1;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int value = current;       // 1. 先記住要交出去的值
                current--;                 // 2. 再前進
                return value;              // 3. 交出記住的值
            }
        };
    }
}

for (int n : new Countdown(3)) { ... }   // 3, 2, 1
```

`next()` 的重點是**先記住、再前進、最後回傳**。如果只回傳卻不前進，`hasNext()` 會永遠是 true，for-each 就變成無限迴圈；如果先前進再回傳，就會漏掉第一個值。

### 兩種方向

步長可能是正數或負數時，「是否還有下一個」的判斷也要分成兩種：

- 往上數：目前的值還**小於**終點
- 往下數：目前的值還**大於**終點

三元運算子（第 03 題）很適合表達這種二選一的判斷。

### 匿名內部類別

`new Iterator<>() { ... }` 是在建立物件的同時定義類別內容。它可以讀取外層物件的欄位，例如上例的 `from`，所以每次呼叫 `iterator()`，都會得到一個從頭開始的新迭代器。

## 常見錯誤

- `next()` 只回傳目前的值，忘了前進，造成無限迴圈
- 先前進再回傳，漏掉第一個元素
- 在 `hasNext()` 裡修改狀態
- 沒有處理負的步長
- 陳述句漏了分號

## 動手做

1. 打開 `Range.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 29_iterator`（Linux / macOS：`./javalings.sh run 29_iterator`）。
3. 卡住時執行 `.\javalings.cmd hint 29_iterator`。如果檢查一直不結束，請先檢查有沒有無限迴圈。

## 延伸（選讀）

- `Iterator` 還有一個選擇性實作的 `remove()` 方法，預設會丟出 `UnsupportedOperationException`。
- 用 for-each 走訪 `ArrayList` 時呼叫 `list.remove(...)`，會丟出 `ConcurrentModificationException`；這時應該改用 `iterator.remove()` 或 `removeIf`。
