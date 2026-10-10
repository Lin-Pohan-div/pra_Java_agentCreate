# 18 繼承與覆寫

> 難度：★★★　主題：繼承、override、super、多型

## 學習目標

- 會用 `extends` 建立子類別
- 會在子類別建構式中用 `super(...)` 呼叫父類別建構式
- 會用 `@Override` 覆寫父類別的方法
- 理解多型：變數型別是父類別時，實際執行的是物件真正型別的方法

## 觀念講解

### 繼承

```java
public class Vehicle {
    private final String brand;

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public int wheels() {
        return 4;
    }

    public String summary() {
        return brand + " has " + wheels() + " wheels";
    }
}

class Bike extends Vehicle {
    public Bike(String brand) {
        super(brand);          // 必須是建構式的第一行
    }

    @Override
    public int wheels() {
        return 2;
    }
}
```

`Bike` 自動擁有 `Vehicle` 的 `summary()`，只覆寫了 `wheels()`。

### super(...)

子類別的建構式**一定會先呼叫父類別的建構式**。父類別沒有無參數建構式時，必須明確寫出 `super(引數)`，而且要放在第一行。

### @Override

`@Override` 告訴編譯器：「我想覆寫父類別的某個方法」。如果方法名稱或參數打錯，編譯器會直接報錯，不會讓你在不知不覺中寫出一個新方法。

### 多型

```java
Vehicle v = new Bike("Giant");
v.summary();     // "Giant has 2 wheels"
```

`summary()` 寫在父類別裡，但它呼叫的 `wheels()` 會依照**物件實際的型別**（`Bike`）來決定。這讓父類別可以定義流程，再交給子類別填入細節。

```java
Vehicle[] all = { new Vehicle("Toyota"), new Bike("Giant") };
for (Vehicle x : all) {
    System.out.println(x.summary());   // 各自呼叫自己的 wheels()
}
```

### 組字串時注意重複

在父類別中組合輸出時，每個片段只放一次。把結果完整寫出來，例如 `"Giant has 2 wheels"`，再逐字和程式比對，最容易發現多出來或缺少的部分。

## 常見錯誤

- 子類別建構式忘了 `super(...)`，或沒把它放在第一行
- 覆寫時方法簽章不一致，又沒加 `@Override`，結果沒有真的覆寫
- 組輸出字串時，某個片段重複或遺漏

## 動手做

1. 打開 `Animal.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 18_inheritance`（Linux / macOS：`./javalings.sh run 18_inheritance`）。
3. 卡住時執行 `.\javalings.cmd hint 18_inheritance`。

## 延伸（選讀）

- 子類別可以用 `super.方法名()` 呼叫父類別的版本，再在它的基礎上加料。
- 宣告為 `final` 的類別不能被繼承，`final` 方法不能被覆寫。
- Java 只允許**單一繼承**（一個類別只能 `extends` 一個父類別）；需要多種能力時，請用第 20 題的介面。
