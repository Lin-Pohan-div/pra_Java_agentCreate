// 先讀同資料夾的 README.md，再動手修改這個檔案。

public class Strings {

    public static String repeat(String s, int times) {
        return s.repeat(times);
    }

    public static boolean isPalindrome(String s) {
        String reversed = new StringBuilder(s).reverse().toString();
        return reversed.equals(s);
    }

    public static String shout(String s) {
        return s.toUpperCase();
    }
}