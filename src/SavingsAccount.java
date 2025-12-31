public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void calculateMonthlyUpdate() {
        setBalance(getBalance() + getBalance() * interestRate);
    }

    @Override
    public String toString() {
        return "Savings " + super.toString() + " Interest rate: " + interestRate;
    }
}
