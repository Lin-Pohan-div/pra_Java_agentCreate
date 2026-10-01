import java.util.ArrayList;

public class NumberList {

    public static ArrayList<Integer> ofRange(int from, int to) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            out.add(i);
        }
        return out;
    }

    public static int total(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return sum;
    }

    public static ArrayList<Integer> doubled(ArrayList<Integer> numbers) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int n : numbers) {
            out.add(n * 2);
        }
        return out;
    }

    public static ArrayList<Integer> evens(ArrayList<Integer> numbers) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int n : numbers) {
            if (n % 2 == 0) {
                out.add(n);
            }
        }
        return out;
    }
}
