public class Money {

    private final int amount;
    private final String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int amount() {
        return amount;
    }

    public String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money other)) {
            return false;
        }
        // TODO: 金額和貨幣都一樣才算相等
        return amount == other.amount
    }

    @Override
    public int hashCode() {
        // TODO: 也要把 currency 算進去，兩個相等的 Money 才會有相同的 hashCode
        return amount;
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
