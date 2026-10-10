# 14 方法重載與可變參數

> 難度：★★★　主題：overloading、varargs

## 學習目標

- 理解方法重載（overloading）：名稱相同、參數列表不同
- 知道編譯器如何依照引數型別選出要呼叫的版本
- 會使用可變參數 `型別... 名稱`，並把它當作陣列處理
- 能讓每個重載版本的行為符合各自的規格

## 觀念講解

### 什麼是重載

同一個類別裡，多個方法可以同名，只要**參數的個數或型別不同**：

```java
public static String format(int n)      { return "#" + n; }
public static String format(double d)   { return String.format("%.2f", d); }
public static String format(int a, int b) { return a + "/" + b; }
```

**只有回傳型別不同不算重載**，會編譯失敗。

### 編譯器怎麼選

呼叫時，編譯器會根據**引數的靜態型別**，挑出最精確的版本：

```java
format(3)       // → format(int)
format(3.0)     // → format(double)
format(3, 4)    // → format(int, int)
```

如果沒有完全吻合的版本，編譯器會嘗試自動轉型，例如 `int` 轉成 `double`。所以呼叫 `format(3)` 時，就算沒有 `int` 版本，也會選到 `double` 版本。

### 每個版本都有自己的規格

重載的幾個版本名稱相同，但各自是**獨立的方法**。複製貼上一個版本來改，最容易留下前一個版本的殘渣。寫完之後，逐一對照規格和測試，檢查每個版本的輸出。

### 可變參數 varargs

```java
public static int count(String... words) {
    return words.length;          // 在方法裡，words 就是 String[]
}

count()                 // 0
count("a")              // 1
count("a", "b", "c")    // 3
count(new String[]{"x", "y"})  // 也可以直接傳陣列
```

規則：

- 一個方法只能有**一個** varargs 參數，而且必須放在**最後**
- 沒有傳任何引數時，會得到一個**長度 0 的陣列**，不是 `null`

## 常見錯誤

- 以為改回傳型別就能重載
- 某個重載版本的輸出格式跟規格不符，因為複製時忘了修改
- varargs 不是最後一個參數
- 沒考慮引數為零個的情況

## 動手做

1. 打開 `Overloads.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 14_methods`（Linux / macOS：`./javalings.sh run 14_methods`）。
3. 卡住時執行 `.\javalings.cmd hint 14_methods`。

## 延伸（選讀）

- 重載（overloading）在**編譯時期**決定呼叫哪個版本；覆寫（overriding，第 18 題）在**執行時期**依物件的實際型別決定。兩者名字相近，但機制完全不同。
- `System.out.println` 就是重載的經典例子：它有 `int`、`double`、`String`、`Object` 等多個版本。
