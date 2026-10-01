import java.util.Comparator;
import java.util.List;

public record Order(String id, int quantity, double unitPrice) {

    public double total() {
        return quantity * unitPrice;
    }

    public static double grandTotal(List<Order> orders) {
        return orders.stream().mapToDouble(Order::total).sum();
    }

    public static Order mostExpensive(List<Order> orders) {
        return orders.stream()
                .max(Comparator.comparingDouble(Order::total))
                .orElseThrow(() ->
                        new IllegalArgumentException("訂單清單是空的，無法找出最貴的一筆"));
    }
}
