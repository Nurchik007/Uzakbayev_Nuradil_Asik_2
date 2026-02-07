public abstract class BankAccount {
    private String accountNumber;
    private double balance;
    private MonthlyUpdatePolicy updatePolicy; // добавляем стратегию

    public BankAccount(String accountNumber, double balance, MonthlyUpdatePolicy updatePolicy) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.updatePolicy = updatePolicy;
    }

    public void calculateMonthlyUpdate() {
        updatePolicy.apply(this);
    }

    // теперь Bank может вызвать этот метод
    public MonthlyUpdatePolicy getUpdatePolicy() {
        return updatePolicy;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) {
        if (balance >= 0) this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account " + accountNumber + " Balance: " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return accountNumber.equals(that.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}
