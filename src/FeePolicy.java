public class FeePolicy implements MonthlyUpdatePolicy {
    private double fee;

    public FeePolicy(double fee) {
        this.fee = fee;
    }

    @Override
    public void apply(BankAccount account) {
        account.setBalance(account.getBalance() - fee);
    }
}
