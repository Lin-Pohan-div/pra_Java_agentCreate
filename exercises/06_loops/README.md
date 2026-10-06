# 06 迴圈 for / while

> 難度：★★　主題：for、while、Character

## 學習目標

- 看得懂 `for` 迴圈的三個部分：初始化、條件、更新
- 會用 `while` 寫「條件成立就繼續」的迴圈，並確保迴圈一定會結束
- 會在迴圈裡用 `if` 篩選要計算的項目
- 會使用 `Character` 的判斷方法

## 觀念講解

### for 迴圈

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);     // 0 1 2 3 4
}
```

| 部分 | 何時執行 | 作用 |
|---|---|---|
| `int i = 0` | 只在一開始執行一次 | 建立計數變數 |
| `i < 5` | 每一輪開始前檢查 | false 就離開迴圈 |
| `i++` | 每一輪結束後 | 推進計數 |

範圍是「含頭不含尾」還是「含頭含尾」，就看條件用 `<` 還是 `<=`。

### 累加模式

「加總」「計數」都是同一個模式：迴圈外先準備一個變數，迴圈內更新它。

```java
int product = 1;
for (int i = 1; i <= 4; i++) {
    product *= i;              // 1*1*2*3*4 = 24
}
```

### 迴圈裡篩選

只想計算符合條件的項目時，把更新的動作包進 `if`：

```java
int spaces = 0;
for (int i = 0; i < text.length(); i++) {
    if (text.charAt(i) == ' ') {
        spaces++;
    }
}
```

### Character 的工具方法

`charAt(i)` 拿到的是 `char`。`Character` 類別提供許多判斷方法：

```java
Character.isDigit('7')      // true
Character.isLetter('x')     // true
Character.isWhitespace(' ') // true
Character.isUpperCase('A')  // true
```

### while 迴圈

```java
int n = 100;
int halvings = 0;
while (n > 1) {
    n /= 2;          // ← 一定要讓條件有機會變成 false
    halvings++;
}
```

`while` 只有條件，沒有內建的「更新」部分，**更新要自己寫在迴圈本體裡**。忘了寫的話，條件永遠成立，程式就會卡在**無限迴圈**。在 Javalings 裡，這會表現為檢查一直不結束。

## 常見錯誤

- `<` 和 `<=` 用錯，多算或少算一次（off-by-one）
- 迴圈裡無條件累加，忘了篩選
- `while` 迴圈沒有更新條件中的變數 → 無限迴圈
- 迴圈本體裡漏了分號

## 動手做

1. 打開 `Loops.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 06_loops`（Linux / macOS：`./javalings.sh run 06_loops`）。
3. 卡住時執行 `.\javalings.cmd hint 06_loops`。如果檢查一直不結束，請先檢查有沒有無限迴圈。

## 延伸（選讀）

- `do { ... } while (條件);` 至少會執行一次。
- `break` 可以提早離開迴圈，`continue` 可以跳過這一輪剩下的部分。
- 走訪字串的每個字元也可以寫成 `for (char c : s.toCharArray())`。
