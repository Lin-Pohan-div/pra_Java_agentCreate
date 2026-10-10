# 02 變數與整數除法

> 難度：★　主題：變數、型別、整數除法

## 學習目標

- 會宣告變數，並理解「型別」決定了變數能放什麼
- 知道 `int / int` 的結果還是 `int`，小數部分會被直接捨去
- 會讓運算以 `double` 進行，保留小數
- 知道 `String` 和數字用 `+` 相接時，結果會變成字串

## 觀念講解

### 宣告變數

```java
int age = 20;            // 整數
double price = 19.9;     // 有小數的數
String name = "Amy";     // 字串
boolean ok = true;       // 真或假
```

Java 是**靜態型別**語言：變數的型別在宣告時就固定了，之後不能把字串放進 `int` 變數。

### 整數除法

這是新手最常踩的坑：

```java
int a = 10;
int b = 4;
System.out.println(a / b);       // 2，不是 2.5
System.out.println(a % b);       // 2，餘數
```

兩個運算元都是 `int` 時，Java 會做**整數除法**，小數部分直接丟掉（不是四捨五入）。

### 讓結果保留小數

只要其中一個運算元是 `double`，整個運算就會以 `double` 進行：

```java
int total = 10;
int count = 4;

double r1 = total / count;            // 2.0  ← 先做整數除法得到 2，才轉成 double
double r2 = (double) total / count;   // 2.5  ← 先把 total 轉型，再除
double r3 = total / 4.0;              // 2.5  ← 4.0 是 double 字面值
```

關鍵是**轉型發生的時機**：`r1` 的結果已經在整數除法時遺失了小數，事後再轉成 `double` 也救不回來。

### 字串和數字相接

```java
int score = 95;
String text = "score=" + score;   // "score=95"
```

`+` 的左邊或右邊只要有一個是 `String`，另一邊就會被轉成字串。

## 常見錯誤

- 以為 `7 / 2` 會得到 `3.5`
- 把 `(double) (a / b)` 當成 `(double) a / b`：前者先做整數除法才轉型，結果仍然是整數值
- 計算出結果存進變數之後，忘了 `return`
- 變數型別選錯：把整數存進 `double`，接成字串時會多出 `.0`

## 動手做

1. 打開 `Variables.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 02_variables`（Linux / macOS：`./javalings.sh run 02_variables`）。
3. 卡住時執行 `.\javalings.cmd hint 02_variables`。

## 延伸（選讀）

- `long` 可以存比 `int` 更大的整數；`int` 的最大值是 `Integer.MAX_VALUE`（約 21 億），超過時會**溢位**變成負數。
- 涉及金錢的計算不要用 `double`（`0.1 + 0.2` 不會剛好等於 `0.3`），實務上會用 `BigDecimal`。
