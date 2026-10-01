public class Grid {

    public static String row(int n) {
        // TODO: 產生 "1*n=1 2*n=2 3*n=3"，項目之間用一個空白，例如
        //       row(2) 要得到 "1*2=2 2*2=4 3*2=6"
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            sb.append(i).append('*').append(n).append('=').append(n * i);
        }
        return sb.toString();
    }

    public static int countPairs(int size) {
        int count = 0;
        // TODO: 從 0..size-1 選出兩個不同位置配對（順序不拘），countPairs(4) 應該是 6
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                count++;
            }
        }
        return count;
    }

    public static int sumMultiples(int limit) {
        // TODO: 1 到 limit-1 之間所有 3 的倍數總和，sumMultiples(10) 應該是 18
        int total = 0;
        for (int i = 1; i < limit; i++) {
            total++;
        }
        return total;
    }
}
