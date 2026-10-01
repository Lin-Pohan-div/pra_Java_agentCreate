public class Operators {

    public static boolean isEven(int n) {
        // TODO: 偶數回傳 true。目前 n % 2 == 1 判斷的是奇數
        return n % 2 == 0;
    }

    public static int max(int a, int b) {
        // TODO: 回傳比較大的那個，max(9, 4) 應該是 9
        return a > b ? a : b;
    }

    public static int clamp(int value, int low, int high) {
        // TODO: 把 value 限制在 [low, high] 之間
        return Math.min(Math.max(value, low), high);
    }
}
