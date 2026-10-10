# 10 二維陣列

> 難度：★★　主題：二維陣列、巢狀迴圈

## 學習目標

- 理解二維陣列是「陣列的陣列」
- 分得清 `m[列][欄]` 兩個索引的意義
- 會用巢狀迴圈走訪整個二維陣列
- 會只用單層迴圈，走訪特定位置，例如某一列、某一欄或對角線

## 觀念講解

### 建立與索引

```java
int[][] seats = {
    {1, 2, 3},     // 第 0 列
    {4, 5, 6},     // 第 1 列
};

seats[1][2]        // 6：第 1 列、第 2 欄
seats.length       // 2：有幾列
seats[0].length    // 3：第 0 列有幾欄

int[][] board = new int[3][4];  // 3 列 4 欄，全部是 0
```

記法：**先列（row）後欄（column）**，跟讀表格一樣，先找哪一行，再找第幾格。

### 巢狀走訪

```java
for (int r = 0; r < seats.length; r++) {
    for (int c = 0; c < seats[r].length; c++) {
        System.out.print(seats[r][c] + " ");
    }
    System.out.println();
}
```

不需要索引時，也可以用兩層 for-each：

```java
for (int[] row : seats) {
    for (int v : row) {
        ...
    }
}
```

### 走訪特定位置

有些路線用一個索引就能描述，這時只要一層迴圈：

```java
// 第 0 欄（每一列的第一格）
for (int r = 0; r < seats.length; r++) {
    total += seats[r][0];
}

// 最後一列
int last = seats.length - 1;
for (int c = 0; c < seats[last].length; c++) {
    total += seats[last][c];
}
```

畫一張小表格，標出你要的格子，再寫下它們的 `[列][欄]`，就能看出兩個索引之間的關係。例如「反對角線」（右上到左下）的格子是 `[0][n-1]`、`[1][n-2]`…，欄索引是 `n - 1 - 列索引`。

## 常見錯誤

- 列和欄的索引對調
- 把某一列或某一欄當成對角線
- 內層迴圈條件用 `m.length`（列數），卻拿來走欄。正方形矩陣不會出錯，換成長方形就越界
- 以為 `new int[n][n]` 需要手動把其他格子設成 0（其實預設就是 0）

## 動手做

1. 打開 `Matrix.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 10_2d_arrays`（Linux / macOS：`./javalings.sh run 10_2d_arrays`）。
3. 卡住時執行 `.\javalings.cmd hint 10_2d_arrays`。

## 延伸（選讀）

- Java 允許「鋸齒陣列」：每一列長度不同，例如 `int[][] t = { {1}, {2, 3}, {4, 5, 6} };`。
- `Arrays.deepToString(m)` 可以一次印出整個二維陣列。
