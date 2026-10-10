# 26 泛型類別

> 難度：★★★　主題：泛型、Function、型別參數

## 學習目標

- 會宣告泛型類別 `class Xxx<T>`，讓同一份程式碼適用於多種型別
- 會寫泛型方法 `<R> R method(...)`
- 會使用 `Function<T, R>`，把「要做的事」當作參數傳進方法
- 知道參考型別的欄位預設是 `null`，以及怎麼正確判斷「沒有值」

## 觀念講解

### 為什麼需要泛型

沒有泛型時，想寫一個能裝任何東西的容器，只能用 `Object`，取出時還得手動轉型，轉錯了要到執行時才會發現。泛型讓**編譯器**幫你檢查型別：

```java
public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A first()  { return first; }
    public B second() { return second; }
}

Pair<String, Integer> p = new Pair<>("age", 30);
String k = p.first();     // 不需要轉型
int v = p.second();
```

`T`、`A`、`B`、`R` 只是**型別參數**的名字，使用時才會被代換成實際的型別。

### null 與「沒有值」

參考型別的欄位如果沒有初始化，預設值是 `null`。`null` 是一個特殊的值，表示「沒有指向任何物件」，它**不是**字串 `"null"`：

```java
String s = null;
s == null        // true
"null".equals(s) // false，"null" 只是一個四個字母的字串
```

判斷「有沒有值」時，直接和 `null` 比較就好。

### Function<T, R>

`java.util.function.Function<T, R>` 代表「吃一個 T、吐一個 R」的函式。它只有一個抽象方法 `apply`：

```java
Function<String, Integer> length = s -> s.length();
length.apply("hello");      // 5

Function<Integer, String> stars = n -> "*".repeat(n);
stars.apply(3);             // "***"
```

### 泛型方法

方法自己也可以宣告型別參數，寫在回傳型別前面：

```java
public class Pair<A, B> {
    ...
    public <C> Pair<C, B> withFirst(Function<A, C> f) {
        return new Pair<>(f.apply(first), second);
    }
}

Pair<String, Integer> p = new Pair<>("age", 30);
Pair<Integer, Integer> q = p.withFirst(s -> s.length());   // (3, 30)
```

`<C>` 的意思是：呼叫時才決定 `C` 是什麼型別，由傳進來的 `Function` 推斷。

## 常見錯誤

- 拿 `null` 和字串 `"null"` 比較
- 拿到 `Function` 參數卻沒有呼叫 `apply`，或直接回傳 `null` 佔位
- 寫成 `new Box<String>()` 雖然沒錯，但 `new Box<>()` 更簡潔
- 泛型參數用基本型別，例如 `Box<int>`，應該用 `Box<Integer>`

## 動手做

1. 打開 `Box.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 26_generics`（Linux / macOS：`./javalings.sh run 26_generics`）。
3. 卡住時執行 `.\javalings.cmd hint 26_generics`。

## 延伸（選讀）

- 有界型別參數：`<T extends Comparable<T>>` 限制 `T` 必須能比較大小，方法裡就能呼叫 `compareTo`。
- 萬用字元 `List<? extends Number>` 和 `List<? super Integer>`（PECS 原則）在寫函式庫時很常見。
- 這個 `Box` 其實就是簡化版的 `Optional`，見第 28 題。
