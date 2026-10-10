# 19 抽象類別

> 難度：★★★　主題：抽象類別、abstract、多型

## 學習目標

- 理解抽象類別：不能直接 `new`，用來當作子類別的共同骨架
- 會宣告抽象方法（只有簽章、沒有本體），並在子類別中實作
- 會在抽象類別裡寫一般方法，呼叫還沒實作的抽象方法
- 會用父類別型別的陣列，以多型方式處理不同子類別

## 觀念講解

### 抽象類別與抽象方法

```java
public abstract class Employee {

    public abstract int monthlyPay();     // 抽象方法：沒有 { }，直接以分號結尾

    public int yearlyPay() {              // 一般方法：可以呼叫抽象方法
        return monthlyPay() * 12;
    }
}

class Engineer extends Employee {
    @Override
    public int monthlyPay() {
        return 70000;
    }
}

class Intern extends Employee {
    @Override
    public int monthlyPay() {
        return 30000;
    }
}
```

- 有抽象方法的類別必須宣告成 `abstract`
- `new Employee()` 會編譯失敗；只能 `new` 具體的子類別
- 具體子類別**必須實作所有**抽象方法，否則它自己也得宣告成 abstract

### 骨架與細節

抽象類別適合放「大家都一樣的流程」，而會變的那一步留給子類別實作。上面的 `yearlyPay()` 不需要知道月薪怎麼算，只要相信每個子類別都會提供 `monthlyPay()`。

### 用父類別處理一群物件

```java
public static int totalYearly(Employee[] staff) {
    int total = 0;
    for (Employee e : staff) {
        total += e.yearlyPay();   // 多型：每個人各自算
    }
    return total;
}
```

彙總函式應該只做「彙總」：照規格把每個元素的值加起來，不要額外加工。

### 再談建構式的 this

跟第 17 題一樣：參數和欄位同名時，沒寫 `this.` 的指定只會改到參數本身。如果欄位是 `final`，編譯器會因為「final 欄位沒有初始化」而報錯。這個錯誤訊息出現在建構式附近時，就該檢查有沒有漏掉 `this.`。

## 常見錯誤

- 抽象方法寫了 `{ }` 本體，或具體方法忘了寫本體
- 子類別沒有實作全部的抽象方法
- 建構式少了 `this.`，欄位沒有被設定
- 彙總時對每個元素多做了規格沒要求的運算
- 在父類別把類別名稱寫死，子類別呼叫時印出的不是自己的名稱；用 `getClass().getSimpleName()` 取得實際型別

## 動手做

1. 打開 `Shape.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 19_abstract`（Linux / macOS：`./javalings.sh run 19_abstract`）。
3. 卡住時執行 `.\javalings.cmd hint 19_abstract`。

## 延伸（選讀）

- `getClass().getSimpleName()` 會回傳物件實際類別的名稱，常用在 `toString` 或除錯輸出。
- 抽象類別和介面（第 20 題）怎麼選？需要**共用欄位或建構邏輯**時用抽象類別；只需要**宣告能力**時用介面。
- Java 17 的 `sealed` 類別可以限制哪些子類別能繼承，例如 `sealed class Shape permits Square, Circle`。
