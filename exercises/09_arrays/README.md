# 09 一維陣列

> 難度：★★　主題：陣列、length、迴圈

## 學習目標

- 會建立陣列，並用索引讀取、寫入元素
- 知道 `length` 是屬性，不是方法
- 會走訪陣列，完成加總、找最大值等常見操作
- 會建立「新陣列」當作結果，不改動傳進來的陣列

## 觀念講解

### 建立陣列

```java
int[] a = {3, 1, 4};          // 直接給初始值
int[] b = new int[5];         // 長度 5，元素預設都是 0
String[] names = new String[2]; // 參考型別預設是 null
```

陣列的長度**建立後就固定**，不能再改。

### 索引與 length

```java
int[] a = {3, 1, 4};
a[0]        // 3，索引從 0 開始
a[2]        // 4，最後一個索引是 length - 1
a.length    // 3，沒有括號！
a[3]        // 執行時丟出 ArrayIndexOutOfBoundsException
```

對照一下：字串的長度是 `s.length()`（方法），陣列的長度是 `a.length`（屬性）。

### 走訪

```java
// 需要索引時
for (int i = 0; i < a.length; i++) {
    System.out.println(i + ": " + a[i]);
}

// 只需要值時（for-each）
for (int v : a) {
    System.out.println(v);
}
```

### 常見模式：找最小值

```java
int smallest = a[0];                 // 用第一個元素當起點
for (int i = 1; i < a.length; i++) {
    if (a[i] < smallest) {
        smallest = a[i];
    }
}
```

起點用 `a[0]` 而不是 `0`，因為陣列裡可能全是負數。

### 產生新陣列

```java
int[] squares = new int[a.length];
for (int i = 0; i < a.length; i++) {
    squares[i] = a[i] * a[i];       // 寫入新陣列，原陣列不動
}
```

「讀出」和「寫入」只差在 `=` 的哪一邊：`x = a[i]` 是讀，`a[i] = x` 是寫。

## 常見錯誤

- 寫成 `a.length()`
- 索引從 1 開始，或迴圈條件用 `<=` 而越界
- 找最大／最小值時，起點用 `0`，遇到全負數就錯
- 要回傳新陣列卻直接改了參數陣列，或寫入時沒有做應有的運算
- 陳述句結尾漏掉分號

## 動手做

1. 打開 `ArrayTools.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 09_arrays`（Linux / macOS：`./javalings.sh run 09_arrays`）。
3. 卡住時執行 `.\javalings.cmd hint 09_arrays`。

## 延伸（選讀）

- `java.util.Arrays` 有很多工具：`Arrays.toString(a)` 方便印出、`Arrays.sort(a)` 排序、`Arrays.copyOf(a, n)` 複製。
- 陣列變數存的是**參考**：`int[] b = a;` 之後改 `b[0]`，`a[0]` 也會跟著變。
