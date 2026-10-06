// 先讀同資料夾的 README.md，再動手修改這個檔案。

public class Overloads {

    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static String describe(int n) {
        return "int " + n;
    }

    public static String describe(String s) {
        // TODO: 這是字串版本，應該要印出字串本身，不要印出型別
        return "String " + s;
    }

    public static int sum(int... values) {
        // TODO: 把所有引數加總，sum(1, 2, 3) 應該是 6，sum() 應該是 0
        int total = 0;
        for (int v : values) {
            total += v;
        }
        return total;
    }
}
