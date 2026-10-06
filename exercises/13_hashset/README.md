# 13 Set 去重

> 難度：★★　主題：Set、LinkedHashSet、去重

## 學習目標

- 理解 `Set` 是「不重複元素」的集合
- 分得清 `HashSet`、`LinkedHashSet`、`TreeSet` 在順序上的差異
- 會利用 `add` 的回傳值偵測重複
- 會在放進集合之前，依正確順序正規化資料

## 觀念講解

### Set 的特性

```java
import java.util.HashSet;
import java.util.Set;

Set<String> tags = new HashSet<>();
tags.add("java");
tags.add("java");     // 已經有了，不會重複加入
tags.size();          // 1
tags.contains("java");// true
```

| 實作 | 走訪順序 |
|---|---|
| `HashSet` | 不保證 |
| `LinkedHashSet` | 依照插入順序 |
| `TreeSet` | 依照元素排序 |

### add 的回傳值

很多人忽略了 `add` 會回傳 `boolean`：

| 回傳 | 意思 |
|---|---|
| `true` | 原本沒有，這次**新加入** |
| `false` | 原本就有，這次**沒有加入**（重複） |

所以「找出第一個重複出現的元素」可以這樣寫：

```java
Set<String> seen = new HashSet<>();
for (String email : emails) {
    if (!seen.add(email)) {
        return email;           // add 失敗 → 之前看過
    }
}
return null;
```

寫判斷時，先把 `true` / `false` 各代表什麼念出來，就不容易把條件寫反。

### 正規化的順序

使用者輸入常常大小寫不一致，前後還夾雜空白。去重前要先把它們變成「標準形式」：

```java
String key = raw.strip().toLowerCase();
```

處理不只一個步驟時，想一想每一步會不會影響下一步。這題的關鍵在於**每一個要求的步驟都要做到**，少做一步，看起來相同的字就會被當成不同元素。

## 常見錯誤

- `add` 回傳值的意義搞反，把「新加入」當成「重複」
- 正規化少做了一步，例如只轉小寫、沒去掉空白
- 用 `HashSet` 卻期待固定的走訪順序
- 拿 `Set` 去存需要保留重複次數的資料（這種需求請用第 12 題的 Map 計數）

## 動手做

1. 打開 `UniqueWords.java`，照著每個 TODO 的規格修正程式。
2. 執行 `.\javalings.cmd run 13_hashset`（Linux / macOS：`./javalings.sh run 13_hashset`）。
3. 卡住時執行 `.\javalings.cmd hint 13_hashset`。

## 延伸（選讀）

- 集合運算：`a.retainAll(b)` 取交集、`a.addAll(b)` 取聯集、`a.removeAll(b)` 取差集（都會直接修改 `a`）。
- 放進 `HashSet` 的自訂物件必須正確實作 `equals` 與 `hashCode`，見第 22 題。
