package domain.accounts;
public class InterestPolicy implements MonthlyUpdatePolicy {
    private double rate;

    public InterestPolicy(double rate) {
        this.rate = rate;
    }

    @Override
    public void apply(BankAccount account) {
        account.setBalance(account.getBalance() * (1 + rate));
    }
}
