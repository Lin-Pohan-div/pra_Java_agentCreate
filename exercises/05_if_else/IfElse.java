public class IfElse {

    public static String grade(int score) {
        // TODO: >=90 回 "A"、>=80 回 "B"、>=70 回 "C"，其餘回 "F"
        if (score >= 90) {
            return "A";
        } else if (score >= 80){
            return "B";
        } else if (score >= 70) {
            return "C";
        } else {
            return "F";
        }
    }

    public static String sign(int n) {
        // TODO: 正數回 "positive"、負數回 "negative"、0 回 "zero"
        if (n > 0) {
            return "positive";
        } else if (n < 0) {
            return "negative"; 
        } else {
            return "zero";
        }
    }

    public static boolean isLeapYear(int year) {
        // TODO: 四年一閏，但能被 100 整除時要改成 400 年一閏
        if (year % 400 == 0) {
            return true;
        }else if (year % 100 == 0) {
            return false;
        }else{
            return year % 4 == 0;
        }
    }
}
