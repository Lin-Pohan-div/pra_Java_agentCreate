# 04 字串常見方法

> 難度：★★　主題：字串、StringBuilder、字串比較

## 學習目標

- 知道 `String` 是物件，操作要透過方法
- 會使用 `repeat`、`toUpperCase`、`toLowerCase`、`length`、`charAt` 等常用方法
- 理解 `==` 和 `equals` 的差別，並且永遠用 `equals` 比較字串內容
- 會用 `StringBuilder` 組字串或反轉字串

## 觀念講解

### String 是物件

`String` 不是基本型別，而是一個類別，操作都要透過「點」呼叫方法：

```java
String s = "Java";
s.length();          // 4
s.charAt(0);         // 'J'
s.toLowerCase();     // "java"
s.toUpperCase();     // "JAVA"
s.substring(1, 3);   // "av"
"-".repeat(5);       // "-----"
```

Java 的方法名稱有區分大小寫，而且慣例是**小駝峰**：`toUpperCase` 的 C 是大寫。拼錯一個字母，編譯器就會說 `cannot find symbol`。

字串是**不可變**（immutable）的：`s.toUpperCase()` 會回傳一個**新的**字串，`s` 本身不會改變。

### String 沒有 `*`

有些語言可以寫 `"ab" * 3`，但 Java 的字串只支援 `+` 串接。要重複字串，請用方法。

### == 和 equals

```java
String a = "hello";
String b = new String("hello");   // 刻意建立一個新物件

a == b        // false！兩個是不同的物件
a.equals(b)   // true，內容相同
```

- `==` 比較的是「兩個變數是不是指向**同一個物件**」
- `equals` 比較的是「**內容**是否相同」

有時候 `==` 會「剛好」得到 true，這是因為字面值會被 JVM 共用。但不能依賴這種行為，**比較字串內容一律用 `equals`**。

### StringBuilder

需要一段一段組字串時，用 `StringBuilder` 比反覆 `+` 更有效率：

```java
StringBuilder sb = new StringBuilder();
sb.append("a").append(1).append('!');
String result = sb.toString();         // "a1!"

new StringBuilder("abc").reverse().toString();   // "cba"
```

## 常見錯誤

- 用 `==` 比較字串內容
- 方法名稱的大小寫打錯，例如 `touppercase`、`ToUpperCase`
- 以為 `s.toUpperCase();` 會直接改掉 `s`，結果忘了接住回傳值
- 想用 `*` 重複字串

## 動手做

1. 打開 `Strings.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 04_strings`（Linux / macOS：`./javalings.sh run 04_strings`）。
3. 卡住時執行 `.\javalings.cmd hint 04_strings`。

## 延伸（選讀）

- `equalsIgnoreCase` 可以忽略大小寫比較；`a.compareTo(b)` 可以比較字典順序。
- `"literal".equals(variable)` 的寫法在 `variable` 是 `null` 時不會丟出例外。
