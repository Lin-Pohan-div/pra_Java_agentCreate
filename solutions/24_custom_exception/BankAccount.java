public class BankAccount {

    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
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
