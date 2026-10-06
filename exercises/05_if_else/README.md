# 05 條件判斷 if / else

> 難度：★　主題：if、else、條件運算子

## 學習目標

- 會寫 `if` / `else if` / `else` 的多分支判斷
- 理解分支是「由上而下、第一個成立就停」
- 會列出所有情況，包含邊界值，不漏掉任何一種
- 會用 `&&`、`||`、`!` 組合複合條件

## 觀念講解

### 基本結構

```java
if (條件一) {
    ...
} else if (條件二) {
    ...
} else {
    ...      // 以上都不成立時
}
```

每個條件都必須是一個**完整的布林運算式**，而且要用一對小括號包起來。

### 由上而下，第一個成立就停

```java
int temp = 35;
if (temp >= 30) {
    return "hot";
} else if (temp >= 20) {      // 執行到這裡時，一定已經是 temp < 30
    return "warm";
} else if (temp >= 10) {
    return "cool";
} else {
    return "cold";
}
```

由於前面的條件已經擋掉了較大的值，後面的條件只要寫下界就好。**順序很重要**：如果把 `>= 10` 放在最上面，35 度也會被判成 `"cool"`。

### 把所有情況列完

寫條件前，先把輸入可能的範圍切成互不重疊的幾段。很多 bug 都出在「中間那個點」：

- 數字：正、負，還有 **0**
- 範圍：剛好在邊界上的值，例如 `>=` 和 `>` 的差別

用表格先想清楚，再寫程式：

| 輸入 | 期望 |
|---|---|
| 正數 | … |
| 0 | … |
| 負數 | … |

### 複合條件

```java
// 平日的上班時間
boolean working = isWeekday && hour >= 9 && hour < 18;

// 週末或國定假日
boolean dayOff = isWeekend || isHoliday;
```

規則包含「但是」「除非」時，先用口語拆成幾個小條件，再用 `&&`、`||` 接起來。必要時加括號，讓優先順序一目了然：

```java
// 會員可以打折，但是生日當月例外，改成送禮
boolean discount = isMember && !isBirthdayMonth;
```

## 常見錯誤

- 條件的小括號沒有成對，或比較式沒寫完整
- 漏掉中間的情況（常見的是 0）
- 多條規則同時存在時，搞錯哪一條優先
- 某條分支忘了 `return`，造成 missing return statement

## 動手做

1. 打開 `IfElse.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 05_if_else`（Linux / macOS：`./javalings.sh run 05_if_else`）。
3. 卡住時執行 `.\javalings.cmd hint 05_if_else`。

## 延伸（選讀）

- 分支很多、又都只比較同一個變數是否等於某些值時，`switch` 會更清楚，見第 07 題。
- 「提早 return」（guard clause）可以減少巢狀層數：先把特殊情況處理完並 `return`，剩下的程式就不用包在 `else` 裡。
