# 17 類別與封裝

> 難度：★★　主題：類別、建構式、this、存取修飾

## 學習目標

- 會寫一個有欄位、建構式和方法的類別
- 理解 `this.x = x` 為什麼需要 `this`
- 理解封裝：欄位設為 `private`，透過方法對外提供存取
- 修改物件狀態時，所有相關欄位都要一起更新

## 觀念講解

### 類別的組成

```java
public class Book {

    private String title;          // 欄位：物件的狀態
    private int pages;

    public Book(String title, int pages) {   // 建構式：名稱和類別相同，沒有回傳型別
        this.title = title;
        this.pages = pages;
    }

    public boolean isLong() {      // 方法：物件的行為
        return pages > 300;
    }

    public String getTitle() {     // getter：對外提供唯讀存取
        return title;
    }
}
```

```java
Book b = new Book("Java 入門", 420);
b.isLong();     // true
b.getTitle();   // "Java 入門"
b.pages;        // 編譯錯誤：pages 是 private
```

### this

建構式的參數名稱常常和欄位一樣。在方法裡，**名稱會優先指向最近的那一個**，也就是參數：

```java
public Book(String title, int pages) {
    title = title;          // 參數指定給自己，欄位完全沒變！
    this.title = title;     // 正確：this.title 是「這個物件的欄位」
}
```

### 封裝

把欄位設為 `private`，只透過方法改變狀態，好處是類別能保證自己的資料一致：

```java
public void addPages(int extra) {
    if (extra > 0) {
        this.pages += extra;
    }
}
```

### 狀態要一起更新

一個操作如果在概念上影響多個欄位，例如「放大」同時影響寬和高，或「搬家」同時影響城市和地址，就要確保每個欄位都有更新。只改一半，物件就會處於不一致的狀態。

## 常見錯誤

- 建構式裡寫 `x = x`，欄位沒有被設定
- 應該同時修改多個欄位的操作只改了一個
- 欄位用 `public`，讓外部可以繞過檢查直接修改

## 動手做

1. 打開 `Rectangle.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 17_class`（Linux / macOS：`./javalings.sh run 17_class`）。
3. 卡住時執行 `.\javalings.cmd hint 17_class`。

## 延伸（選讀）

- 欄位加上 `final`，就只能在建構式裡設定一次。不會變的物件（immutable）比較容易推理，也比較安全。
- 用 `double` 比較是否相等（`==`）時要小心浮點誤差；這題的測試只用整齊的數字。
