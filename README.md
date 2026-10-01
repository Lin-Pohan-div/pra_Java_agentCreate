# Javalings

用「修正壞掉的程式碼」的方式學 Java，概念與操作體驗都照著 [Rustlings](https://github.com/rust-lang/rustlings) 走。

每一題都是一個小程式碼，裡面刻意留了 1～3 個錯誤。你要做的是把它改到**能編譯、而且通過測試**，
然後執行 `.\javalings.cmd run`（Linux / macOS 為 `./javalings.sh run`）讓它持續盯著：
每次存檔都會自動重新檢查，通過後直接跳到下一題。測試都是寫好的，不用改。

```
檢查中: 01_hello — 打招呼
  D:\path\exercises\01_hello\Hello.java
✗ 編譯失敗，程式還不能跑。

編譯錯誤:
  D:\path\exercises\01_hello\Hello.java:5:50: error: ';' expected
提示：閱讀錯誤訊息中的檔名與行號。

監看中：存檔後會自動重新檢查（Ctrl+C 離開）
```

## 環境需求

只需要 **JDK 17 以上**，沒有任何第三方相依套件（JUnit 之類的都沒用）。

```bash
java -version    # 需要 17 或以上
```

必須是 JDK 而不是只有 JRE：CLI 用 JDK 內建的編譯器 API 來編譯練習，啟動腳本也要用 `javac` 編譯 CLI 本身。
Windows 的啟動腳本另外會用到系統內建的 PowerShell。

## 開始

### Windows

```powershell
.\javalings.cmd
```

PowerShell（VS Code 預設終端機）不會從目前目錄找指令，一定要加 `.\`，
否則會出現「無法辨識 'javalings.cmd'」。在 cmd 中 `.\javalings.cmd` 同樣可用。

### Linux / macOS

```bash
chmod +x javalings.sh
./javalings.sh
```

啟動腳本會把 `src/main/java` 編譯到 `build/classes`。之後只有在 CLI 原始碼有變動時才會重新編譯，
否則直接啟動。第一個畫面會告訴你現在該改哪個檔案：

```
Javalings 0.1.0
透過修正有問題的 Java 程式碼來學習 Java。

目前進度: 0/30 完成

目前練習: 01_hello — 打招呼
  檔案: D:\path\exercises\01_hello\Hello.java
  指令: run 開始監看（存檔自動檢查）/ hint 看提示 / list 看全部

第一次玩？執行 .\javalings.cmd guide ／ 全部指令：.\javalings.cmd help
```

第一次玩建議先執行 `.\javalings.cmd guide` 看新手教學。
接著執行 `.\javalings.cmd run`，然後打開那個檔案改。`run` 會一直盯著目前這題：

- 每次存檔，畫面會清空並顯示最新的檢查結果
- 通過後自動跳到下一題，並立刻顯示下一題目前的錯誤
- 全部完成時自動結束；中途想離開按 `Ctrl+C`
- 測試超過 10 秒沒跑完會判定逾時（通常是無窮迴圈），改好存檔即可繼續

CLI 會從目前目錄往上找含有 `exercises/` 的資料夾當作專案根目錄，所以在子資料夾裡執行也可以。

## 指令

以下指令都接在啟動腳本後面，例如 `.\javalings.cmd list`、`./javalings.sh hint 15_recursion`。
沒有指定練習時，一律針對「目前練習」。

| 指令 | 縮寫 | 說明 |
| --- | --- | --- |
| `run [練習]` | `r` | 監看練習檔，存檔就自動編譯並測試，通過後跟到下一個未完成的題目 |
| `verify [練習]` | `v` | 只檢查一次就結束，適合 CI。通過一樣會記錄進度並推進到下一題 |
| `list` | `ls` | 列出所有練習與完成狀態 |
| `hint [練習]` | `h` | 顯示提示 |
| `reset [練習]` | | 把該題標記為未完成，並把原始碼還原成原題（舊檔備份到 `.javalings/backup/`） |
| `reset --all` | `reset -a` | 清除全部進度，**不會**還原任何原始碼 |
| `guide` | | 新手教學：怎麼玩、目前該改哪個檔、卡住怎麼辦 |
| `help` | `-h`、`--help` | 列出所有指令 |
| `version` | `-v`、`--version` | 顯示版本 |

指定練習的方式，依序比對：

1. 完整 id，例如 `15_recursion`
2. id 開頭，例如 `15`
3. 名稱或主題關鍵字，例如 `遞迴`、`HashMap`（取第一個符合的）

`run` 通過一題後，會從該題往後找下一個未完成的題目，找到最後一題還沒有就從頭繞回來。

選項：`--no-color` 關閉色彩、`--dir <路徑>` 指定練習目錄（進度檔會存在該目錄的上一層）。

`verify` 在**未通過時會回傳 exit code 1**，通過時回傳 0，可以直接拿去串 CI。
`run` 是給人用的監看模式，不會自己結束（除非全部完成），不要拿去串 CI。

## 練習分成什麼

| # | 主題 | 題數 |
| --- | --- | --- |
| 01–04 | 語法基礎：方法、變數、整數除法、運算子、字串 | 4 |
| 05–08 | 流程控制：if/else、迴圈、switch、巢狀迴圈 | 4 |
| 09–13 | 陣列與集合：一維/二維陣列、ArrayList、HashMap、Set | 5 |
| 14–17 | 方法設計：重載、varargs、遞迴、static 與實例狀態、封裝 | 4 |
| 18–22 | 物件導向：繼承、抽象類別、介面、enum、equals/hashCode | 5 |
| 23–25 | 例外處理：丟出與捕捉、自訂例外、try-with-resources | 3 |
| 26–30 | 進階：泛型、Stream、Optional、自訂 Iterator、record | 5 |

難度從 ★ 到 ★★★★★，建議照順序做。

## 專案結構

```
javalings.cmd / javalings.sh   啟動腳本：必要時編譯 CLI，然後啟動
pom.xml                        想用 Maven 也可以，但非必要
src/main/java/dev/javaling/    CLI 本體
  Javalings.java               指令進入點與各指令實作
  Constants.java               版本、逾時秒數等共用常數
  cli/                         終端機輸出（色彩、編碼）與表格
  model/                       練習的載入與進度保存
  verify/                      編譯（JDK 編譯器 API）、在獨立 JVM 執行測試、判定結果
  util/Json.java               讀 exercise.json 用的小型 JSON 解析器
  testkit/TestKit.java         練習用的斷言工具
exercises/                     練習本體（每題一個資料夾）
solutions/                     參考解答
tools/build.ps1                Windows 啟動腳本用的建置腳本
tools/selftest.ps1             自我驗證腳本（給出題者用）
build/classes/                 CLI 編譯產物（自動產生）
.javalings/                    進度與 reset 備份（自動產生）
```

### 每題的組成

```
exercises/01_hello/
  exercise.json   題目名稱、難度、主題、提示
  Hello.java      ← 你要改這個
  HelloTest.java  ← 驗收測試，不用改
  .original/
    Hello.java.orig  原題備份，reset 01_hello 從這裡還原（不用打開）
  .javalings-out/    檢查時的編譯產物（自動產生，可以忽略）
```

原題用 `.orig` 副檔名，CLI 和編輯器都不會把它當成第二個 Java 檔。

`exercise.json` 長這樣：

```json
{
  "name": "打招呼",
  "difficulty": "★",
  "topics": ["語法", "方法", "字串"],
  "hint": "Java 的每個陳述句都要以分號 ; 結尾。\n方法要回傳值，就必須用 return 把值送回去。\n字串相加請用 + 連接兩個 String。"
}
```

`HelloTest.java` 用的是內建的 `TestKit`，不用 JUnit：

```java
import dev.javaling.testkit.TestKit;

public class HelloTest {
    public static void main(String[] args) {
        TestKit.expectEquals("greet(\"World\")", "Hello, World!", Hello.greet("World"));
        TestKit.done();
    }
}
```

`TestKit` 提供 `expectEquals`、`expectTrue`／`expectFalse`、`expectClose`（浮點數）、
`expectNotNull`、`expectThrows`、`expectThrowsMessage`（連例外訊息都檢查）、`fail`，
以及最後一定要呼叫的 `done()`。只有呼叫到 `done()` 才算通過；漏寫的話，測試永遠不會過。

## 關於 solutions/

`solutions/` 放著每一題的參考解答，建議**卡住時才看**。

它的另一個用途是自我驗證：

```powershell
powershell -ExecutionPolicy Bypass -File tools\selftest.ps1
```

這個腳本會做這些檢查，任何一項不過就回傳非 0：

1. 確認每題的 `.original/` 都有原題，而且和練習檔內容一致（代表 `reset` 還原得回正確的題目）
2. 確認所有練習在**未修改**的狀態下都會失敗（代表題目有意義，沒有人是白送的）
3. 把 `solutions/` 的解答換上去，確認所有測試都會通過（代表題目可解、測試寫對）

它是給出題者用的：學生改過題目之後再跑，第 1、2 項自然會失敗。
另外，腳本開頭和結尾都會執行 `reset --all`，**會清掉目前的進度**（原始碼不受影響）。

## 想自己加題目

新增 `exercises/NN_name/` 資料夾，放進 `exercise.json`、一個待修的 `.java`、一個 `*Test.java`，
CLI 會自動收錄（依資料夾名稱排序）。再把待修的 `.java` 複製成 `.original/<檔名>.orig`
（例如 `.original/Hello.java.orig`）、參考解答放到 `solutions/NN_name/`，`reset` 與 `selftest.ps1` 才找得到。

幾個限制：

- 每題**剛好一個**非 `Test` 結尾的 `.java`，和**剛好一個** `*Test.java`
- 需要多個類別時，把額外的類別寫在同一個 `.java` 檔裡（其中一個 `public`）
- 缺少 `exercise.json`、待修檔或測試檔的資料夾會被**直接略過**，不會報錯；新題目沒出現在 `list` 裡時，先檢查這三個檔案

## 常見問題

**沒有顏色？**
顏色由 CLI 自己判斷：在 Windows 上只有 Windows Terminal 和 ConEmu 會開啟顏色，
傳統主控台和 VS Code 內建終端機都會自動關閉；輸出被導向檔案時也不會有顏色。
想強制關閉可以加上 `--no-color`，例如 `.\javalings.cmd list --no-color`。

**看到 `v`、`x`、`->` 而不是 `✓`、`✗`、`→`？**
CLI 會用終端機本身的編碼輸出（例如繁體中文 Windows 的 cp950），中文可以正常顯示，
但這個編碼沒有的符號會自動換成 ASCII 替代字，不是壞掉。

**進度存在哪？**
`.javalings/progress.txt`，已經被 `.gitignore` 忽略。刪掉它就會重新開始。

**想改掉我改壞的答案？**
執行 `reset <id>`（例如 `.\javalings.cmd reset 01_hello`），會用該題 `.original/` 裡的原題覆蓋練習檔。
覆蓋前會把你改過的版本備份到 `.javalings/backup/<id>/`（每題只保留最後一份），按錯了可以從那裡拿回來。
`reset --all` 只清除進度，不會還原任何原始碼，以免一次蓋掉所有作業。

**可以用 Maven 嗎？**
可以，`mvn package` 會產出 `target/javalings.jar`，在專案根目錄執行 `java -jar target/javalings.jar` 即可。
不過預設的啟動腳本只依賴 JDK 本身，不需要 Maven，離線也能用。

## 授權

隨意使用、修改、拿去做自己的教學教材。
