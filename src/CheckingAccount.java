public class CheckingAccount extends BankAccount {
    private double fee;

    // исправленный конструктор: передаём стратегию FeePolicy в super
    public CheckingAccount(String accountNumber, double balance, double fee) {
        super(accountNumber, balance, new FeePolicy(fee));
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Checking " + super.toString() + " Fee: " + fee;
    }
}
