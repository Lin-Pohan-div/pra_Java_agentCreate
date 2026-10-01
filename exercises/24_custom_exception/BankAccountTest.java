import dev.javaling.testkit.TestKit;

public class BankAccountTest {

    public static void main(String[] args) {
        BankAccount account = new BankAccount(100);
        account.withdraw(30);
        TestKit.expectEquals("提領 30 後餘額", 70, account.balance());

        account.deposit(50);
        TestKit.expectEquals("存入 50 後餘額", 120, account.balance());

        // 剛好等於餘額應該成功
        account.withdraw(120);
        TestKit.expectEquals("提光後餘額", 0, account.balance());

        BankAccount poor = new BankAccount(10);
        TestKit.expectThrowsMessage("餘額不足要丟出自訂例外",
                InsufficientFundsException.class, "500",
                () -> poor.withdraw(500));
        TestKit.expectEquals("失敗後餘額不變", 10, poor.balance());
        TestKit.done();
    }
}
