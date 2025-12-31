public class CheckingAccount extends BankAccount {
    private double fee;

    public CheckingAccount(String accountNumber, double balance, double fee) {
        super(accountNumber, balance);
        this.fee = fee;
    }

    @Override
    public void calculateMonthlyUpdate() {
        setBalance(getBalance() - fee);
    }

    @Override
    public String toString() {
        return "Checking " + super.toString() + " Fee: " + fee;
    }
}
