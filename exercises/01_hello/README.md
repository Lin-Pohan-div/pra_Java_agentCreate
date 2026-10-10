# 01 打招呼

> 難度：★　主題：語法、方法、字串

## 學習目標

- 看得懂一個 Java 類別和方法的基本骨架
- 知道每個陳述句都要用分號 `;` 結尾
- 會用 `return` 把方法的結果交回給呼叫者
- 會用 `+` 把字串和變數接起來

## 觀念講解

### 類別與方法

Java 的程式碼一定要放在**類別**（class）裡面，而能被執行的動作則寫成**方法**（method）：

```java
public class Calculator {

    public static int square(int x) {
        return x * x;
    }
}
```

逐段拆開來看：

| 片段 | 意思 |
|---|---|
| `public static` | 先當成固定寫法，之後的題目會解釋 |
| `int` | **回傳型別**：這個方法算完會交出一個整數 |
| `square` | 方法名稱 |
| `(int x)` | **參數**：呼叫時要傳進來一個整數，在方法裡叫做 `x` |
| `{ ... }` | 方法本體 |

### 陳述句與分號

Java 用分號來判斷「一句話在哪裡結束」，而不是用換行。少了分號，編譯器會回報 `';' expected`，並指出行號。

```java
int a = 3;
int b = a + 1;
```

### return

回傳型別不是 `void` 的方法，**每一條可能的執行路徑都必須 `return` 一個值**。只把結果存進區域變數是不夠的：變數在方法結束後就消失了，呼叫者什麼也拿不到。

```java
public static int addOne(int x) {
    int result = x + 1;
    return result;        // 少了這行 → missing return statement
}
```

### 字串串接

用 `+` 可以把字串和變數接在一起：

```java
String city = "Taipei";
String text = "I live in " + city + ".";   // "I live in Taipei."
```

注意：加了雙引號的 `"city"` 是**字面上的六個字母**，不是變數 `city` 的值。

### 方法重載預告

同一個類別裡可以有兩個名字相同、參數不同的方法。不帶參數的版本常常會用預設值，去呼叫帶參數的版本。第 14 題會詳細介紹。

## 常見錯誤

- 漏掉行尾的分號：錯誤訊息會指向**下一個** token，所以實際出錯的位置可能在回報行號的上一行
- Java 區分大小寫：把 `String` 寫成 `string`，會得到 `cannot find symbol`
- 回傳型別不是 `void`，卻忘了寫 `return`
- 想用變數的值，卻把變數名稱寫進雙引號裡

## 動手做

1. 打開 `Hello.java`，照著 TODO 的規格修正程式。
2. 在專案根目錄執行 `.\javalings.cmd run 01_hello`（Linux / macOS：`./javalings.sh run 01_hello`），之後每次存檔都會自動檢查。
3. 卡住時執行 `.\javalings.cmd hint 01_hello` 看提示。想重來可以執行 `reset 01_hello`。

## 延伸（選讀）

- `String.format("Hello, %s!", name)` 和 `"Hello, %s!".formatted(name)`（Java 15+）都可以用模板產生字串，參數一多時比 `+` 好讀。
- 編譯錯誤和執行時錯誤是兩回事：這一題的問題全部都會在編譯階段被抓到。
