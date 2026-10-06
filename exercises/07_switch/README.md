# 07 switch 敘述

> 難度：★★　主題：switch、case、char

## 學習目標

- 會寫傳統的 `switch` 敘述：`case 值:` 和 `default:`
- 理解 fall-through：沒有 `break` 或 `return` 時，會一路往下執行
- 會利用 fall-through，讓多個 `case` 共用同一段程式
- 知道 `default` 負責「其他所有情況」，它的結果要合理

## 觀念講解

### 基本語法

```java
switch (month) {
    case 1:
        return "January";
    case 2:
        return "February";
    default:
        return "?";
}
```

- `switch (...)` 括號裡放要比對的值，可以是 `int`、`char`、`String`、enum 等
- 每個 `case` 後面是**一個常數**，接著一個**冒號** `:`
- `default:` 處理所有沒被列到的值

### fall-through

傳統 `switch` 比對到某個 `case` 後，會**從那裡開始一直往下執行**，直到遇到 `break`、`return` 或整個 `switch` 結束：

```java
switch (level) {
    case 3:
        System.out.println("gold");
    case 2:
        System.out.println("silver");   // level 為 3 時也會印出
        break;
    case 1:
        System.out.println("bronze");
}
```

如果每個 `case` 都直接 `return`，方法當場結束，也就不需要 `break`。

### 刻意利用 fall-through

幾個值要做同一件事時，把 `case` 疊在一起：

```java
switch (day) {
    case 6:
    case 7:
        return "weekend";
    default:
        return "weekday";
}
```

### default 的意義

`default` 代表「上面都沒有命中」。寫的時候問自己：**沒被列出來的值，答案應該是什麼？** 如果 `case` 列的是「符合的那幾種」，`default` 通常就是「不符合」。

### 列舉完整的情況

規格說「0、1、2 各自對應一個結果，其餘歸到另一類」時，每一個有特定結果的值都要有自己的 `case`。少列一個，它就會掉進 `default`。

## 常見錯誤

- `case` 後面漏了冒號
- 忘了 `break`，結果意外執行到下一個 `case`
- `default` 回傳的值跟「沒命中」的語意相反
- 少列了某個 `case`，讓本來有特定結果的值落入 `default`

## 動手做

1. 打開 `Switch.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 07_switch`（Linux / macOS：`./javalings.sh run 07_switch`）。
3. 卡住時執行 `.\javalings.cmd hint 07_switch`。

## 延伸（選讀）

- Java 14 起有 **switch 運算式**，用 `->` 取代冒號，而且不會 fall-through：

  ```java
  String kind = switch (day) {
      case 6, 7 -> "weekend";
      default -> "weekday";
  };
  ```

  第 21 題會用到這種寫法。
