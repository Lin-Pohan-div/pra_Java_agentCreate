// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.util.List;

public record Order(String id, int quantity, double unitPrice) {

    public double total() {
        // TODO: 數量乘以單價
        return 0;
    }

    public static double grandTotal(List<Order> orders) {
        // TODO: 加總所有訂單金額，空清單要回傳 0.0
        return -1;
    }

    public static Order mostExpensive(List<Order> orders) {
        // TODO: 找出金額最大的訂單；清單為空時丟出
        //       IllegalArgumentException，且訊息要包含「空」
        throw new UnsupportedOperationException("尚未實作");
    }
}
