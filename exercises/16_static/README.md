# 16 static 與實例狀態

> 難度：★★★　主題：static、物件、狀態

## 學習目標

- 分得清**實例欄位**（每個物件各一份）和**static 欄位**（整個類別共用一份）
- 知道 static 方法裡不能直接使用實例欄位
- 會判斷一份資料應該屬於「物件」還是「類別」

## 觀念講解

### 類別 vs. 物件

類別是設計圖，物件是照著設計圖做出來的實體。

```java
public class Player {
    private String name;               // 實例欄位：每個 Player 各有一個名字
    private static int online = 0;     // static 欄位：整個遊戲共用一個在線人數

    public Player(String name) {
        this.name = name;
        online++;                      // 每建立一位玩家，共用的計數加一
    }

    public String name() {             // 實例方法：要透過某個物件呼叫
        return name;
    }

    public static int online() {       // static 方法：透過類別呼叫
        return online;
    }
}
```

```java
Player a = new Player("Amy");
Player b = new Player("Ben");
a.name();          // "Amy"
b.name();          // "Ben"：各自獨立
Player.online();   // 2：共用一份
```

### 判斷問題：這份資料屬於誰？

| 問自己 | 答案是 | 用 |
|---|---|---|
| 不同物件的這個值應該可以不一樣嗎？ | 是 | 實例欄位 |
| 這個值描述的是「全體」或「類別本身」嗎？ | 是 | `static` 欄位 |

如果把應該各自獨立的資料宣告成 `static`，所有物件就會共用同一份，改了一個，其他物件也跟著變。

### static 方法的限制

static 方法不屬於任何一個物件，所以裡面**沒有 `this`**，也不能直接讀取實例欄位：

```java
public static int online() {
    return name.length();   // 編譯錯誤：non-static variable name cannot be referenced from a static context
}
```

看到這個錯誤訊息時，代表有一邊的 `static` 用錯了：可能是這個方法不該是 static，也可能是這個欄位應該是 static。

## 常見錯誤

- 把每個物件應該各自擁有的資料宣告成 `static`
- 把需要跨物件共享的計數器宣告成實例欄位，結果每個物件都從 0 開始算
- 在 static 方法裡存取實例欄位
- 用物件呼叫 static 方法（`a.online()`）。雖然能編譯，但容易讓人誤會

## 動手做

1. 打開 `Counter.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 16_static`（Linux / macOS：`./javalings.sh run 16_static`）。
3. 卡住時執行 `.\javalings.cmd hint 16_static`。

## 延伸（選讀）

- `static final` 常用來宣告常數，例如 `public static final int MAX_LEVEL = 99;`。
- 這一題的測試會建立多個物件，並比較計數的變化。之後在其他程式裡用 static 欄位存狀態時要小心，它的值會在整個程式執行期間一直存在。
