import dev.javaling.testkit.TestKit;

import java.util.HashSet;
import java.util.Set;

public class MoneyTest {

    public static void main(String[] args) {
        Money usd = new Money(100, "USD");
        Money same = new Money(100, "USD");
        Money eur = new Money(100, "EUR");

        TestKit.expectTrue("相同金額與貨幣相等", usd.equals(same));
        TestKit.expectTrue("貨幣字串內容相同就相等",
                usd.equals(new Money(100, new String("USD"))));
        TestKit.expectFalse("貨幣不同不相等", usd.equals(eur));
        TestKit.expectFalse("和 null 不相等", usd.equals(null));
        TestKit.expectFalse("和字串不相等", usd.equals("100 USD"));

        TestKit.expectEquals("相等的物件 hashCode 相同", usd.hashCode(), same.hashCode());
        TestKit.expectEquals("toString()", "100 USD", usd.toString());

        Set<Money> set = new HashSet<>();
        set.add(usd);
        set.add(same);
        TestKit.expectEquals("放進 Set 應該被去重", 1, set.size());
        TestKit.done();
    }
}
