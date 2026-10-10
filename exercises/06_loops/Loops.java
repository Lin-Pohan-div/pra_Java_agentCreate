// 先讀同資料夾的 README.md，再動手修改這個檔案。

public class Loops {

    public static int sumTo(int n) {
        int total = 0;
        // TODO: 把 1 到 n 加總，sumTo(5) 應該是 15
        for (int i = 1; i < n; i++) {
            total += i;
        }
        return total;
    }

    public static int countLetters(String s) {
        int count = 0;
        // TODO: 只計算英文字母，countLetters("a1b!") 應該是 2
        for (int i = 0; i < s.length(); i++) {
            count++;
        }
        return count;
    }

    public static int countDown(int from) {
        int steps = 0;
        // TODO: 從 from 每次減 1 數到 0，數出總共幾步，countDown(3) 應該是 3
        while (from > 0) {
            steps++;
        }
        return steps;
    }
}
