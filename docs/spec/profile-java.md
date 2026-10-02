# Java Profile（profile-java）

| 項目 | 內容 |
| --- | --- |
| 文件版本 | 1.1 |
| 實作名稱 | Javalings |
| 依據版本 | 0.1.1（`Constants.VERSION`、`pom.xml`） |
| 核心規格 | [core-spec.md](core-spec.md) |
| Profile 代碼 | `JAVA` |

本文件是 [core-spec.md](core-spec.md) 的 Java 實作規格，內容描述 Javalings 0.1.1 的實際行為。
第 0 章填寫核心規格[附錄 A](core-spec.md#附錄-aprofile-必填參數表) 的必填參數表；第 1～8 章與核心規格逐章對應，
每一條 `JAVA-<章>-NN` 需求都以 `→ CORE-…` 標示它實作的核心需求。
沒有在本文件出現的核心需求，表示 Java 實作照核心規格的原文執行，沒有額外的 Java 專屬細節。

原始碼位置以 `src/main/java/dev/javaling/` 為基準，以下簡寫為 `dev/javaling/`。

---

## 0. Profile 必填參數表（已填）

| 編號 | 參數 | Java 的值 |
| --- | --- | --- |
| P-01 | Profile 代碼 | `JAVA` |
| P-02 | 工具名稱與版本 | 名稱 `Javalings`，版本 `0.1.1`；定義在 `dev/javaling/Constants.java` 的 `NAME`、`VERSION`，`pom.xml` 的 `<version>` 必須一致 |
| P-03 | 啟動指令 | Windows：`.\javalings.cmd`；其他作業系統：`./javalings.sh`。依 `os.name` 是否含 `win`（不分大小寫）決定 |
| P-04 | 工具簡介 | 「透過修正有問題的 Java 程式碼來學習 Java。」 |
| P-05 | 執行環境需求 | JDK 17 以上（必須是 JDK，不能只有 JRE）；Windows 另需系統內建的 PowerShell。沒有任何第三方相依 |
| P-06 | 狀態目錄 | `.javalings`；進度檔第一行為 `# javalings progress - 請勿手動編輯` |
| P-07 | 學生可編輯檔辨識規則 | 題目資料夾中副檔名為 `.java`、檔名**不以** `Test.java` 結尾的一般檔案。每題必須**恰好 1 個**（N = 1）；超過時略過該題並顯示警告 |
| P-08 | 檢查器辨識規則 | 題目資料夾中檔名以 `Test.java` 結尾的一般檔案。每題必須恰好 1 個；超過時略過該題並顯示警告 |
| P-09 | 中繼資料檔 | `exercise.json`；自製 JSON 解析器 `dev/javaling/util/Json.java`；非字串值以 `String.valueOf` 轉換；格式錯誤的原因訊息見 JAVA-FMT-07 |
| P-10 | 原題命名 | 原題資料夾 `.original/`；原題檔為 `<學生可編輯檔名>.orig`，例如 `.original/Hello.java.orig` |
| P-11 | 產物目錄 | `.javalings-out/` |
| P-12 | 檢查階段清單 | 1. `compile`（建置類，無逾時）→ 2. `test`（測試類，逾時 10 秒） |
| P-13 | 隔離執行方式 | 新開一個 JVM：`<java.home>/bin/java[.exe] -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp <產物目錄><路徑分隔符><CLI 自身位置> <測試類別名>` |
| P-14 | 逾時 | `Constants.TEST_TIMEOUT_MS = 10_000`（10 秒）；以 `Process.destroyForcibly()` 強制終止 |
| P-15 | 診斷格式 | `<檔案路徑>:<行>:<欄>: error: <訊息>` |
| P-16 | 通過與失敗標記 | 通過標記 `JAVALINGS_TESTS_PASSED`；失敗標記前綴 `JAVALINGS_TESTS_FAILED`（只用於輸出清理，TestKit 不會印出） |
| P-17 | 檢查器函式庫 | 自製的 `dev.javaling.testkit.TestKit`，不使用 JUnit；浮點容許誤差 `1e-9`；檢查器的進入點是測試類別的 `public static void main(String[])` |
| P-18 | 外部資源 | 不適用。Javalings 的題目不使用外部資源，因此不實作 CORE-VER-16 這個擴充點 |
| P-19 | 監看輪詢間隔 | 1 秒（`Javalings.WATCH_POLL_MS = 1_000`） |
| P-20 | 色彩支援判斷 | `System.console()` 不為 null，而且「不是 Windows」或「環境變數 `WT_SESSION` 或 `ConEmuANSI` 存在」 |
| P-21 | 終端機編碼偵測 | 依序取系統屬性 `stdout.encoding`、`sun.stdout.encoding`、`native.encoding` 中第一個存在且 JVM 支援的值；都沒有時用 `Charset.defaultCharset()` |
| P-22 | 清畫面方式 | Windows：執行 `cmd /c cls`（繼承 IO）；其他：輸出 `ESC[H ESC[2J ESC[3J` |
| P-23 | 全形字元範圍 | 見 JAVA-OUT-07 |
| P-24 | 啟動腳本與增量建置 | `javalings.cmd` + `tools/build.ps1`（Windows）、`javalings.sh`（Linux／macOS）；建置標記 `build/.last-build`；產物 `build/classes/` |
| P-25 | 自我驗證工具 | `tools/selftest.ps1`，以 `powershell -ExecutionPolicy Bypass -File tools\selftest.ps1` 在 Windows 執行 |
| P-26 | 打包 | Maven：`mvn package` 產出 `target/javalings.jar`，以 `java -jar target/javalings.jar` 執行 |

---

## 1. 指令列介面（CLI）

### 1.1 程式進入點

**JAVA-CLI-01** → CORE-CLI-01
CLI 的進入點是 `dev.javaling.Javalings.main(String[])`，所有指令的實作都在同一個類別中。

**JAVA-CLI-02** → CORE-CLI-02、CORE-CLI-03
選項的處理順序：先移除第一個出現的 `--no-color`；再找第一個 `--dir`，如果它後面還有參數，就把兩者一起移除。重複出現的同名選項只處理第一個，其餘的留在參數列中。

**JAVA-CLI-03** → CORE-CLI-04
指令以 `toLowerCase(Locale.ROOT)` 轉成小寫後比對。

**JAVA-CLI-04** → CORE-CLI-02
使用 `--dir` 時，練習目錄為 `Path.of(<路徑>).toAbsolutePath().normalize()`，專案根目錄為它的 `getParent()`。

**JAVA-CLI-05** → CORE-CLI-06、CORE-CLI-07
沒有 `--dir` 時，從 `Path.of("").toAbsolutePath()` 開始逐層往上，找第一個含有 `exercises` 子資料夾的目錄。啟動腳本不會切換工作目錄，所以學生必須在專案根目錄或其子資料夾內執行。

### 1.2 啟動錯誤訊息

**JAVA-CLI-06** → CORE-CLI-08 ～ CORE-CLI-11
各啟動檢查失敗時的訊息（紅色，exit code 1）：

| 情況 | 訊息 |
| --- | --- |
| 找不到專案根目錄 | 找不到練習目錄。請在專案根目錄執行，或用 --dir 指定路徑。 |
| 練習目錄不存在 | 找不到練習資料夾：<絕對路徑> |
| 沒有任何練習 | 沒有找到任何練習：<絕對路徑> |
| 進度檔讀取失敗 | 無法讀取進度檔：<原因> |

**JAVA-CLI-07** → CORE-CLI-12
`Json` 解析失敗時一律拋出 `IllegalArgumentException`。`ExerciseLoader` 把它（以及 `IndexOutOfBoundsException`）轉成 `IOException`，訊息為「<練習 id>/exercise.json 格式錯誤：<原因>」，再由 `main` 以紅色顯示並以 exit code 1 結束。例如：

```
03_operators/exercise.json 格式錯誤：JSON 數值或格式錯誤，位置 27
```

**JAVA-CLI-13** → CORE-CLI-43
`ExerciseLoader.load(Path, List<String>)` 把警告收集到清單中，`main` 在載入成功後以 `Console.warn`（黃色）逐條顯示，然後才讀取進度、執行指令。

### 1.3 各指令的 Java 專屬細節

**JAVA-CLI-08** → CORE-CLI-21
`help` 的用法說明內容如下（`<L>` 為啟動指令）：

```
用法: <L> [指令] [練習 id]

  run [id]          監看練習檔，存檔就自動檢查，通過後跟到下一題
  verify [id]       只檢查一次並回傳 exit code（適合 CI）
  list              列出所有練習與進度
  hint [id]         顯示提示
  reset [id]        將練習標記為未完成，並把原始碼還原成原題
  reset --all       清除所有進度（不還原原始碼）
  guide             新手教學：怎麼玩、卡住怎麼辦
  help              顯示本說明
  version           顯示版本

選項: --no-color 關閉色彩、--dir <路徑> 指定練習目錄
```

**JAVA-CLI-09** → CORE-CLI-22
`guide` 的「卡住了怎麼辦」第 3 點為「真的不會，再去看 solutions/ 裡的參考解答」；「常見問題」的色彩說明為「沒有顏色？傳統主控台不支援色碼，改用 Windows Terminal 就有。」

**JAVA-CLI-10** → CORE-PROG-08
所有練習都完成時，顯示的訊息為「全部練習都完成了，去寫自己的 Java 吧！」。

**JAVA-CLI-11** → CORE-CLI-17
找不到練習時，訊息中的參數列以 Java `List.toString()` 的格式顯示，例如「找不到練習：[abc]」。

**JAVA-CLI-12** → CORE-CLI-42
「進度檔寫入失敗」只涵蓋指令執行期間拋出的 `IOException`。

---

## 2. 進度模型（PROG）

**JAVA-PROG-01** → CORE-PROG-01、CORE-PROG-02
進度由 `dev/javaling/model/AppState.java` 管理，檔案位置為 `<專案根目錄>/.javalings/progress.txt`，寫入時以 `\n` 換行、以 UTF-8 編碼。完成集合使用 `LinkedHashSet`，所以會保留加入順序。

**JAVA-PROG-07** → CORE-PROG-04
`AppState.load` 讀完進度檔後，以 `done.removeIf(...)` 移除不在練習清單中的 id，然後才修正目前練習（CORE-PROG-05）。

**JAVA-PROG-02** → CORE-PROG-11
進度掃描判斷「未修改」的條件為：`.original/<學生可編輯檔名>.orig` 與學生可編輯檔都是一般檔案，而且 `Files.mismatch(...) == -1`。比對時發生 `IOException` 視為「已修改」，交給實際檢查判斷。

**JAVA-PROG-03** → CORE-PROG-11
進度掃描的檢查與一般檢查相同，都使用 CLI 自身的 classpath，因此每一題都會實際編譯一次。題數多時，掃描要花一段時間。

**JAVA-PROG-04** → CORE-PROG-12
掃描進度的那一行以 `\r` 回到行首覆寫；結束時輸出 `\r`、30 個空白、`\r` 來清除它。

**JAVA-PROG-05** → CORE-PROG-14
備份路徑為 `<專案根目錄>/.javalings/backup/<練習 id>/<學生可編輯檔名>`，以 `StandardCopyOption.REPLACE_EXISTING` 覆寫。

**JAVA-PROG-06** → CORE-CLI-38
`reset --all` 刪除每一題的 `.javalings-out/` 時，由深到淺逐一刪除；任何刪除失敗都靜默略過。

---

## 3. 驗證流程（VER）

### 3.1 階段宣告

**JAVA-VER-01** → CORE-VER-02、CORE-VER-03
檢查由 `dev/javaling/verify/Verifier.java` 串接，階段如下：

| 順序 | 階段 | 實作 | 失敗分類 | 逾時 |
| --- | --- | --- | --- | --- |
| 1 | `compile` | `verify/Compiler.java`，在 CLI 行程內呼叫 JDK 編譯器 API | 建置失敗（`COMPILE_ERROR`） | 無 |
| 2 | `test` | `verify/Runner.java`，在獨立 JVM 中執行測試類別 | 測試失敗（`TEST_FAILED`）；逾時為內部錯誤（`INTERNAL_ERROR`） | 10 秒 |

結果分類對應 `Verifier.Status` 的 `PASSED`、`COMPILE_ERROR`、`TEST_FAILED`、`INTERNAL_ERROR`。

### 3.2 compile 階段

**JAVA-VER-02** → CORE-VER-04、CORE-VER-05
編譯的規則：

- 編譯器由 `ToolProvider.getSystemJavaCompiler()` 取得，不呼叫外部的 `javac` 指令。取不到編譯器（只有 JRE）時，結果為建置失敗，診斷為「找不到 Java 編譯器，請確認執行環境是 JDK 而不是 JRE。」
- 編譯前清空 `<題目資料夾>/.javalings-out/` 並重新建立。建立失敗時，結果為建置失敗，診斷為「無法準備輸出資料夾：<原因>」。
- 學生可編輯檔與測試檔一起編譯，輸出到產物目錄。
- 編譯選項為 `-encoding UTF-8 -Xlint:none`；能取得 CLI 自身位置時，另外加上 `-classpath <CLI 自身位置>`，讓測試可以 `import dev.javaling.testkit.TestKit`。
- 編譯器直接寫到 stdout 的內容一律丟棄，只收集診斷（diagnostics）。
- 編譯成功的條件為：編譯工作回傳成功，**而且**沒有任何 `ERROR` 類診斷。

**JAVA-VER-03** → CORE-VER-15
每一則 `ERROR` 診斷的格式為 `<檔案路徑>:<行>:<欄>: error: <訊息>`。行號或欄號不大於 0 時省略該段；沒有來源檔時只輸出 `error: <訊息>`。訊息使用 JVM 預設語系（`Locale.getDefault()`）。`WARNING` 與 `MANDATORY_WARNING` 會收集起來，但不顯示。

**JAVA-VER-04** → CORE-VER-15
「編譯錯誤」區塊顯示全部的 `ERROR` 診斷，不只第一則。

**JAVA-VER-05** → CORE-VER-06
「CLI 自身位置」由 `Console.class` 的 `CodeSource` 取得：以啟動腳本執行時是 `build/classes` 目錄，以 Maven 打包執行時是 `javalings.jar`。取得失敗時為 null，此時編譯與執行都不額外加上 classpath。

### 3.3 test 階段

**JAVA-VER-06** → CORE-VER-06、CORE-VER-12
測試行程的規則：

- 執行檔為 `<java.home>/bin/java`（Windows 為 `java.exe`），也就是與 CLI 相同的 JVM。
- 參數為 `-Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp <產物目錄>[<路徑分隔符><CLI 自身位置>] <測試類別名>`。
- 測試類別名為測試檔名去掉 `.java`，位於預設套件（default package）。
- 以 `redirectErrorStream(true)` 合併 stderr 與 stdout。
- 輸出由名為 `javalings-test-output` 的 daemon 執行緒以 UTF-8 逐行讀取。

**JAVA-VER-07** → CORE-VER-11
測試行程超過 10 秒還沒結束時，會呼叫 `destroyForcibly()`，最多再等讀取執行緒 1 秒，然後以已擷取的輸出回報逾時。

**JAVA-VER-08** → CORE-VER-08
測試階段的例外情況：

| 情況 | 結果分類 | 擷取的輸出 |
| --- | --- | --- |
| 測試行程無法啟動 | 測試失敗 | 「無法啟動測試程序：<原因>」 |
| 等待時 CLI 執行緒被中斷 | 測試失敗 | 「測試被中斷。」 |
| 讀取輸出時發生 IO 錯誤 | 依 exit code 與標記判定 | 輸出最後附加「讀取測試輸出失敗：<原因>」 |

**JAVA-VER-09** → CORE-VER-10
逾時的說明訊息為「測試逾時（超過 10 秒），可能有無窮迴圈。」秒數由 `TEST_TIMEOUT_MS / 1000` 算出。

**JAVA-VER-10** → CORE-VER-15
測試失敗時，`Verifier` 會產生說明訊息：清理後的輸出為空時是「測試沒有輸出結果，可能缺少 TestKit.done()。」，否則是「測試失敗。」。CLI 只在輸出為空時以暗色顯示這段訊息（取代「測試輸出」區塊）；輸出不為空時顯示「測試輸出」區塊，不顯示「測試失敗。」。

**JAVA-VER-11** → CORE-VER-14
輸出清理由 `Runner.Result.cleanOutput()` 實作：先以 `\R` 分行，再以 `strip()` 後的內容判斷是否丟棄該行，保留下來的行維持原樣（含縮排），最後對整體做 `strip()`。

---

## 4. 題目格式契約（FMT）

### 4.1 題目資料夾

**JAVA-FMT-01** → CORE-FMT-05
一個題目資料夾的內容：

```
exercises/01_hello/
  exercise.json        中繼資料
  Hello.java           學生可編輯檔
  HelloTest.java       檢查器（測試）
  .original/
    Hello.java.orig    原題
  .javalings-out/      產物目錄（自動產生）
```

**JAVA-FMT-02** → CORE-FMT-03、CORE-FMT-06
學生可編輯檔與檢查器的辨識由 `dev/javaling/model/ExerciseLoader.java` 實作。它把題目資料夾的直接一般檔案分類：

- 不以 `.java` 結尾的檔案略過。
- 以 `Test.java` 結尾的檔案歸為檢查器。
- 其他 `.java` 檔歸為學生可編輯檔。

沒有 `exercise.json`、沒有學生可編輯檔或沒有檢查器時，該資料夾會被靜默略過。兩類檔案都有，但任一類超過 1 個時，該資料夾也會被略過，並產生警告：

```
略過 02_variables：每題只能有一個待修的 .java 和一個 *Test.java，目前有 2 個待修檔、1 個測試檔。
```

個數檢查在解析 `exercise.json` 之前進行，所以被略過的題目不會因為 JSON 格式錯誤而中止 CLI。

**JAVA-FMT-03** → CORE-FMT-05
需要多個類別時，必須把額外的類別寫在學生可編輯檔中，而且其中只能有一個是 `public` 類別。

**JAVA-FMT-04** → CORE-FMT-07
原題檔使用 `.orig` 副檔名，目的是避免載入器把它當成第二個 `.java` 原始檔，也避免編輯器或 IDE 把它當成 Java 原始碼編譯。原題資料夾以 `.` 開頭，載入練習目錄時也不會被誤當成題目。

**JAVA-FMT-05** → CORE-FMT-02
練習目錄下以 `.` 開頭的子資料夾會被略過，其他子資料夾依 `getFileName().toString()` 的字串順序排列。

### 4.2 中繼資料

**JAVA-FMT-06** → CORE-FMT-09、CORE-FMT-10
`exercise.json` 的範例：

```json
{
  "name": "打招呼",
  "difficulty": "★",
  "topics": ["語法", "方法", "字串"],
  "hint": "Java 的每個陳述句都要以分號 ; 結尾。\n方法要回傳值，就必須用 return 把值送回去。\n字串相加請用 + 連接兩個 String。"
}
```

非字串值以 `String.valueOf` 轉換。JSON 的數字一律解析成 `Double`，所以 `"topics": [1]` 會變成主題 `"1.0"`。`topics` 不是陣列時（例如 `"topics": "迴圈"`），以 `String.valueOf` 轉成單一主題。

**JAVA-FMT-07** → CORE-FMT-09
`dev/javaling/util/Json.java` 只支援 JSON 的子集，規則如下：

- 支援物件、陣列、字串、數字、`true`、`false`、`null`。
- 字串跳脫支援 `\"`、`\\`、`\/`、`\b`、`\f`、`\n`、`\r`、`\t`、`\uXXXX`；其他跳脫字元的錯誤為「不支援的跳脫字元 \<字元>」，`\u` 後面不是 4 位十六進位數字時為「JSON \u 跳脫字元格式錯誤，位置 <n>」。
- 數字由 `-+.eE0123456789` 組成的連續字元以 `Double.valueOf` 解析；無法解析（包括該位置根本不是合法的值）時為「JSON 數值或格式錯誤，位置 <n>」。
- 其他錯誤訊息：「JSON 內容意外結尾」「JSON 物件格式錯誤，位置 <n>」「JSON 陣列格式錯誤，位置 <n>」「JSON 字面值錯誤，位置 <n>」「預期 '<c>' 但讀到 '<c>'，位置 <n>」。
- 根值之後的多餘內容不檢查。
- 物件中重複的鍵以最後一個為準。

### 4.3 參考解答

**JAVA-FMT-08** → CORE-FMT-08
參考解答為 `solutions/<練習 id>/<學生可編輯檔名>`，例如 `solutions/01_hello/Hello.java`。

### 4.4 新增題目的步驟

**JAVA-FMT-09** → CORE-FMT-12
新增一題的步驟：

1. 建立 `exercises/NN_name/`，放入 `exercise.json`、一個待修的 `.java`、一個 `*Test.java`。
2. 把待修的 `.java` 複製成 `.original/<檔名>.orig`。
3. 把參考解答放到 `solutions/NN_name/<檔名>`。
4. 執行 `tools/selftest.ps1` 確認三項不變量都成立（第 8 章）。

---

## 5. 檢查器函式庫契約（KIT）

**JAVA-KIT-01** → CORE-KIT-01、CORE-KIT-08
檢查器是預設套件中的一個類別，在 `main` 中依序呼叫斷言，最後呼叫 `TestKit.done()`：

```java
import dev.javaling.testkit.TestKit;

public class HelloTest {
    public static void main(String[] args) {
        TestKit.expectEquals("greet(\"World\")", "Hello, World!", Hello.greet("World"));
        TestKit.done();
    }
}
```

`TestKit` 位於 CLI 自身的 classpath（JAVA-VER-05），在編譯與執行階段都可以取用。

**JAVA-KIT-02** → CORE-KIT-01、CORE-KIT-03
`TestKit.done()` 以 `System.out.println` 印出 `JAVALINGS_TESTS_PASSED`，然後 `flush()`。它不會結束行程，所以 `done()` 之後如果又拋出未捕捉的例外，exit code 不為 0，仍然判定為不通過。

**JAVA-KIT-03** → CORE-KIT-02
斷言失敗時拋出 `AssertionError`。檢查器不捕捉它，所以 JVM 會把堆疊追蹤印到 stderr（併入擷取的輸出），並以 exit code 1 結束。

**JAVA-KIT-04** → CORE-KIT-05
`TestKit` 的 API：

| 方法 | 通過條件 |
| --- | --- |
| `expectEquals(String label, Object expected, Object actual)` | 深度相等（見 JAVA-KIT-05） |
| `expectEquals(Object expected, Object actual)` | 同上，標籤固定為「結果」 |
| `expectTrue(String label, boolean value)` | `value == true` |
| `expectFalse(String label, boolean value)` | `value == false` |
| `expectClose(String label, double expected, double actual)` | `Math.abs(expected - actual) <= 1e-9` |
| `expectNotNull(String label, Object value)` | `value != null` |
| `expectThrows(String label, Class<? extends Throwable> type, Runnable body)` | `body` 拋出的 `Throwable` 是 `type` 的實例 |
| `expectThrowsMessage(String label, Class<? extends Throwable> type, String messagePart, Runnable body)` | 同上，而且例外訊息（null 視為 `"null"`）包含 `messagePart` |
| `fail(String message)` | 一律失敗，直接拋出 `AssertionError(message)` |
| `done()` | 印出通過標記 |

**JAVA-KIT-05** → CORE-KIT-05
深度相等的規則：兩者都是 `int[]`、`long[]`、`double[]`、`char[]` 或 `boolean[]` 時使用 `Arrays.equals`；兩者都是 `Object[]` 時使用 `Arrays.deepEquals`；其他情況使用 `Objects.equals`。

**JAVA-KIT-06** → CORE-KIT-06、CORE-KIT-07
失敗訊息為 `<label>\n  期望: <期望>\n  實際: <實際>`。各斷言填入的值：

| 斷言 | 期望 | 實際 |
| --- | --- | --- |
| `expectEquals` | 展開後的期望值 | 展開後的實際值 |
| `expectTrue`／`expectFalse` | `true`／`false` | `false`／`true` |
| `expectClose` | `String.valueOf(expected)` | `String.valueOf(actual)` |
| `expectNotNull` | `非 null` | `null` |
| `expectThrows`（類型不對） | 類型的簡單名稱 | `<實際類型簡單名稱>: <訊息>` |
| `expectThrows`（沒拋出） | 類型的簡單名稱 | `（沒有丟出例外）` |
| `expectThrowsMessage`（類型不對） | 類型的簡單名稱 | `<實際類型簡單名稱>: <訊息>` |
| `expectThrowsMessage`（訊息不符） | `<類型> 且訊息包含 "<文字>"` | `<類型>: <實際訊息>` |
| `expectThrowsMessage`（沒拋出） | `<類型> 且訊息包含 "<文字>"` | `（沒有丟出例外）` |

「展開」是指：基本型別陣列使用 `Arrays.toString`，`Object[]` 使用 `Arrays.deepToString`，null 顯示為 `null`，其他使用 `String.valueOf`。

**JAVA-KIT-07** → CORE-KIT-04
`TestKit` 不會輸出失敗標記 `JAVALINGS_TESTS_FAILED`；這個常數只用於 CLI 端的輸出清理。

---

## 6. 終端機輸出（OUT）

**JAVA-OUT-01** → CORE-OUT-01、CORE-OUT-02
色彩判斷由 `dev/javaling/cli/Console.java` 在類別載入時進行一次（見 P-20），`--no-color` 會呼叫 `Console.enableColor(false)` 覆寫判斷結果。在 Windows 上，傳統主控台與 VS Code 內建終端機都沒有 `WT_SESSION`，所以會關閉色彩；只有 Windows Terminal 與 ConEmu 會開啟。

**JAVA-OUT-02** → CORE-OUT-03
ANSI 色碼：粗體 `ESC[1m`、暗色 `ESC[2m`、紅 `ESC[31m`、綠 `ESC[32m`、黃 `ESC[33m`、藍 `ESC[34m`、青 `ESC[36m`，結尾一律加上 `ESC[0m`。

**JAVA-OUT-03** → CORE-OUT-04
所有輸出都經過一個以終端機編碼（P-21）建立的 `PrintStream`，它直接包裝 `FileDescriptor.out`，不使用 `System.out`。在 JDK 19 以上，接上主控台時 `stdout.encoding` 是主控台字碼頁，輸出被導向檔案或管線時則退回系統原生編碼；JDK 17、18 則使用 `sun.stdout.encoding`。

**JAVA-OUT-04** → CORE-OUT-05
替代字的處理：整段文字可以編碼時原樣輸出；否則逐字元檢查，無法編碼的字元查替代表，查不到時輸出 `?`。

**JAVA-OUT-05** → CORE-OUT-08
清畫面前會先 `flush` 輸出。在 Windows 上，`cmd /c cls` 啟動失敗時會靜默略過。

**JAVA-OUT-06** → CORE-OUT-09
表格由 `dev/javaling/cli/Table.java` 實作。框線的水平線使用 `─`（U+2500），因此在 cp950 下會依 JAVA-OUT-04 替換成 `-`。

**JAVA-OUT-07** → CORE-OUT-10
計算顯示寬度時，從 `ESC` 開始到下一個 `m` 為止的字元都不計寬度。下列範圍的字元計為 2 格，其餘計為 1 格：

| 範圍 | 內容 |
| --- | --- |
| U+1100 ～ U+115F | 韓文字母 |
| U+2013 ～ U+2014 | – — |
| U+2018 ～ U+201F | 彎引號 |
| U+2E80 ～ U+A4CF | 中日韓部首、標點、假名、漢字等 |
| U+AC00 ～ U+D7A3 | 韓文音節 |
| U+F900 ～ U+FAFF | 中日韓相容漢字 |
| U+FE30 ～ U+FE6F | 中日韓相容形式、小型變體 |
| U+FF00 ～ U+FF60 | 全形 ASCII |
| U+FFE0 ～ U+FFE6 | 全形符號 |

---

## 7. 啟動腳本（BOOT）

### 7.1 Windows

**JAVA-BOOT-01** → CORE-BOOT-01 ～ CORE-BOOT-04
`javalings.cmd` 的流程：

1. 以 `powershell -NoProfile -ExecutionPolicy Bypass -File <腳本目錄>\tools\build.ps1` 執行增量建置；失敗時 `exit /b 1`。
2. 執行 `java -cp <腳本目錄>\build\classes dev.javaling.Javalings %*`，並以它的 `ERRORLEVEL` 結束。

**JAVA-BOOT-02** → CORE-BOOT-02、CORE-BOOT-03
`tools/build.ps1` 的規則：

- 收集 `src\main\java` 下所有 `*.java`；一個都沒有時，顯示 `[javalings] No Java sources found under src\main\java.` 並以 exit code 1 結束。
- 建置標記為 `build\.last-build`。標記存在，而且所有原始碼的 `LastWriteTimeUtc` 都不比標記新時，直接以 exit code 0 結束，不重新建置。
- 需要重新建置時，先顯示 `[javalings] Building...`，再執行 `javac -encoding UTF-8 -d build\classes <所有原始碼>`。
- `javac` 失敗時，顯示 `[javalings] Build failed. Is a JDK installed and javac on PATH?` 並以 exit code 1 結束；成功時把 `ok` 以 ASCII 寫入建置標記。

**JAVA-BOOT-03** → CORE-BOOT-06
`javalings.cmd` 不執行 `chcp`；CLI 會以主控台本身的字碼頁輸出。

### 7.2 Linux／macOS

**JAVA-BOOT-04** → CORE-BOOT-01 ～ CORE-BOOT-04
`javalings.sh` 的規則：

- 以 `set -euo pipefail` 執行，任何指令失敗都會讓腳本以非 0 結束。
- 建置標記為 `build/.last-build`。標記存在，而且 `find src/main/java -name '*.java' -newer <標記>` 找不到任何檔案時，不重新建置。
- 需要重新建置時，先顯示 `[javalings] Building...`，接著建立 `build/classes`，以 `find … -print0 | xargs -0 javac -encoding UTF-8 -d build/classes` 編譯，成功後把 `ok` 寫入標記。建置失敗時，除了 `javac` 本身的輸出之外，沒有額外的訊息。
- 以 `exec java -cp build/classes dev.javaling.Javalings "$@"` 啟動，CLI 的 exit code 就是腳本的 exit code。
- 第一次使用前需要 `chmod +x javalings.sh`。

### 7.3 共通

**JAVA-BOOT-05** → CORE-BOOT-02
增量建置只比較修改時間：刪除原始碼檔案不會觸發重新建置，而且 `build/classes` 中舊的 `.class` 檔不會被清除。

**JAVA-BOOT-06** → CORE-BOOT-07
兩個啟動腳本都不檢查 Java 版本。實際的版本下限來自原始碼使用的語言功能（record、`switch` 箭頭語法、`instanceof` 樣式比對、`Stream.toList()`），以及 `pom.xml` 的 `maven.compiler.release = 17`。

**JAVA-BOOT-07** → CORE-BOOT-08
`.gitignore` 排除 `build/`、`target/`、`out/`、`.javalings/` 與 `**/.javalings-out/`。

---

## 8. 出題者工具（TOOL）

### 8.1 tools/selftest.ps1

**JAVA-TOOL-01** → CORE-TOOL-02
腳本切換到專案根目錄後，以 `javac -encoding UTF-8 -d build\classes` 重新編譯全部 CLI 原始碼（不檢查建置標記）；編譯失敗時顯示「編譯失敗」並以 exit code 1 結束。

**JAVA-TOOL-02** → CORE-TOOL-01
檢查對象是 `exercises` 下所有子資料夾（`Get-ChildItem -Directory`，依名稱排序），**不套用** CLI 的略過規則（CORE-FMT-03）。

**JAVA-TOOL-03** → CORE-TOOL-01（檔案個數、原題一致）
對每一題，先計算不以 `Test.java` 結尾的 `.java` 檔與 `*Test.java` 檔的個數，規則與 JAVA-FMT-02 相同。任一個數不是 1 時，記為「檔案數量不對」，這一題不再做原題一致與解答必過的檢查。

個數正確時，以 `Get-FileHash` 比對學生可編輯檔和 `.original/<檔名>.orig`。原題不存在時記為「缺原題」，雜湊不同時記為「與原題不一致」。

「原題必敗」檢查仍會對這類題目執行 `verify`；因為 CLI 會略過它，`verify` 回傳「找不到練習」（exit code 1），不會被誤判為「未修改就通過」。

**JAVA-TOOL-04** → CORE-TOOL-01（原題必敗）、CORE-TOOL-04
先執行 `reset --all`，再對每一題執行 `java -cp build\classes dev.javaling.Javalings --no-color verify <id>`（丟棄所有輸出）。exit code 為 0 時記為「未修改就通過」。

**JAVA-TOOL-05** → CORE-TOOL-01（解答必過）、CORE-TOOL-03
對每一題：

1. 建立暫存目錄 `build\selftest\exercises\<id>\`，複製 `exercise.json`、測試檔與學生可編輯檔進去。
2. 參考解答 `solutions\<id>\<學生可編輯檔名>` 不存在時記為「缺解答」，跳過這一題。
3. 用參考解答覆蓋暫存目錄中的學生可編輯檔。
4. 執行 `… Javalings --no-color --dir build\selftest\exercises verify <id>`，exit code 為 0 時顯示 `PASS`；否則顯示 `FAIL` 和壓縮成一行的輸出，並記為「解答未通過」。

暫存副本的進度檔會寫到 `build\selftest\.javalings\`，不影響正式的進度。全部完成後刪除 `build\selftest`，並再執行一次 `reset --all`。

**JAVA-TOOL-06** → CORE-TOOL-05
沒有任何問題時，顯示「全部通過：<題數> 題的測試與解答一致。」並以 exit code 0 結束；否則顯示「發現 <數量> 個問題：」和逐項清單，並以 exit code 1 結束。

**JAVA-TOOL-07** → CORE-TOOL-01
腳本設定 `$ErrorActionPreference = 'Stop'`，只能在 Windows PowerShell 上執行；目前沒有 Linux／macOS 版的自我驗證工具。

### 8.2 Maven 打包

**JAVA-TOOL-08** → CORE-TOOL-07
`pom.xml` 的設定：

| 項目 | 值 |
| --- | --- |
| 座標 | `dev.javaling:javalings:0.1.1`，packaging `jar` |
| 編譯目標 | `maven.compiler.release = 17`，原始碼編碼 UTF-8 |
| 產物 | `target/javalings.jar`（`finalName = javalings`） |
| 主類別 | `dev.javaling.Javalings`（寫入 manifest） |
| 相依 | 無 |

以 `java -jar target/javalings.jar` 在專案根目錄執行。這時 CLI 自身位置是 jar 檔，`TestKit` 會從 jar 中取得。
