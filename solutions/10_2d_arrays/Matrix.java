public class Matrix {

    public static int[][] identity(int n) {
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            m[i][i] = 1;
        }
        return m;
    }

    public static int sumOfAll(int[][] m) {
        int total = 0;
        for (int[] row : m) {
            for (int v : row) {
                total += v;
            }
        }
        return total;
    }

    public static int sumMainDiagonal(int[][] m) {
        int total = 0;
        for (int i = 0; i < m.length; i++) {
            total += m[i][i];
        }
        return total;
    }
}
