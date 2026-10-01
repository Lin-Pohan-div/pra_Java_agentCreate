import dev.javaling.testkit.TestKit;

import java.util.List;

public class OrderTest {

    public static void main(String[] args) {
        Order small = new Order("A", 2, 10.0);
        Order big = new Order("B", 1, 25.0);

        TestKit.expectClose("total()", 20.0, small.total());
        TestKit.expectClose("total()", 25.0, big.total());
        TestKit.expectEquals("record 自動產生 accessor", "A", small.id());
        TestKit.expectEquals("record 自動產生 equals",
                new Order("A", 2, 10.0), small);

        TestKit.expectClose("grandTotal",
                45.0, Order.grandTotal(List.of(small, big)));
        TestKit.expectClose("grandTotal 空清單",
                0.0, Order.grandTotal(List.of()));

        TestKit.expectEquals("mostExpensive", big, Order.mostExpensive(List.of(small, big)));
        TestKit.expectEquals("mostExpensive 順序不拘",
                big, Order.mostExpensive(List.of(big, small)));
        TestKit.expectThrowsMessage("空清單要丟出有說明的例外",
                IllegalArgumentException.class, "空",
                () -> Order.mostExpensive(List.of()));
        TestKit.done();
    }
}
