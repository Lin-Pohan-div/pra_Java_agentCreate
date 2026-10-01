public class Grid {

    public static String row(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            if (i > 1) {
                sb.append(' ');
            }
            sb.append(i).append('*').append(n).append('=').append(n * i);
        }
        return sb.toString();
    }

    public static int countPairs(int size) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                count++;
            }
        }
        return count;
    }

    public static int sumMultiples(int limit) {
        int total = 0;
        for (int i = 1; i < limit; i++) {
            if (i % 3 == 0) {
                total += i;
            }
        }
        return total;
    }
}
