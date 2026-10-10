# 20 介面與 default 方法

> 難度：★★★　主題：介面、default、static、lambda

## 學習目標

- 會宣告介面，並用 `implements` 實作它
- 知道介面中的抽象方法只有簽章，以分號結尾
- 會使用 `default` 方法，在介面裡提供共用行為
- 會使用介面的 `static` 工廠方法，並看懂用 lambda 實作只有一個抽象方法的介面

## 觀念講解

### 介面是「能力的約定」

```java
public interface Greeter {

    String greeting();                          // 抽象方法：預設就是 public abstract

    default String greetTwice() {               // default 方法：有本體，實作者可以直接用
        return greeting() + " " + greeting();
    }

    static Greeter fixed(String text) {         // static 方法：用介面名稱呼叫
        return () -> text;
    }
}
```

```java
class English implements Greeter {
    @Override
    public String greeting() {
        return "Hi";
    }
}

new English().greetTwice();         // "Hi Hi"
Greeter.fixed("Yo").greetTwice();   // "Yo Yo"
```

### 抽象方法的寫法

介面裡的抽象方法**沒有大括號**，直接以分號結束：

```java
String greeting();      // 正確
String greeting()       // 少了分號 → 編譯錯誤
```

### default 方法的分工

`default` 方法定義「怎麼使用」，實作類別只需要提供最基本的那一塊。分工清楚時，每一層只做自己的部分，不要在兩個地方重複做同一件事，也不要漏做。

舉例來說，如果 default 方法負責在外層加框，實作類別就只要提供框裡的內容，並且照規格把內容做完整。

### lambda 與函式介面

只有**一個抽象方法**的介面稱為**函式介面**，可以用 lambda 建立實作：

```java
Greeter g = () -> "Hello";      // 等同於寫一個類別實作 greeting()
g.greeting();                   // "Hello"
```

### 介面 vs. 抽象類別

| | 介面 | 抽象類別 |
|---|---|---|
| 一個類別可以實作／繼承幾個 | 多個 `implements A, B` | 只能一個 `extends` |
| 欄位 | 只能有常數 | 可以有一般欄位 |
| 建構式 | 沒有 | 有 |

## 常見錯誤

- 介面裡的抽象方法漏了分號，或多寫了 `{ }`
- 實作方法沒有宣告為 `public`（介面方法預設是 public，實作時不能縮小權限）
- 實作類別的輸出和 default 方法的責任重疊或缺漏
- 想直接 `new` 一個介面

## 動手做

1. 打開 `Printable.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 20_interface`（Linux / macOS：`./javalings.sh run 20_interface`）。
3. 卡住時執行 `.\javalings.cmd hint 20_interface`。

## 延伸（選讀）

- 標準函式庫中常見的函式介面有 `Runnable`、`Comparator<T>`、`Function<T, R>`、`Predicate<T>`，第 26、27 題會用到。
- 加上 `@FunctionalInterface` 標註後，如果介面不小心有了第二個抽象方法，編譯器就會報錯。
