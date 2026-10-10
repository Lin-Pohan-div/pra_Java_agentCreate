// 先讀同資料夾的 README.md，再動手修改這個檔案。

public class Matrix {

    public static int[][] identity(int n) {
        // TODO: 產生 n x n 的單位矩陣：對角線是 1，其餘是 0
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            m[i][i] = 1;
        }
        return m;
    }

    public static int sumOfAll(int[][] m) {
        // TODO: 回傳矩陣中所有元素的總和，sumOfAll([[1,2],[3,4]]) 應該是 10
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
        // TODO: 累加主對角線，sumMainDiagonal([[1,2],[3,4]]) 應該是 5
        for (int i = 0; i < m.length; i++) {
            total += m[i][i];
        }
        return total;
    }
}
