// 先讀同資料夾的 README.md，再動手修改這個檔案。

public class BankAccount {

    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        // TODO: 餘額不足時丟出 InsufficientFundsException，
        //       否則就 balance -= amount
        throw new IllegalStateException("尚未實作");
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int balance() {
        return balance;
    }
}

class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(int requested, int available) {
        super("餘額不足：需要 " + requested + "，可用 " + available);
    }
}
