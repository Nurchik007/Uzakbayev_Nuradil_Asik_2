package domain.accounts;
public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        // исправлено: передаём стратегию InterestPolicy в super
        super(accountNumber, balance, new InterestPolicy(interestRate));
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Savings " + super.toString() + " Interest rate: " + interestRate;
    }
}
