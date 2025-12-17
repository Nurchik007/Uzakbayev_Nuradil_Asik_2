public
class BankAccount {
    String accountNumber;
    double balance;
    String accountType;
    public BankAccount(String accNum, double bal, String type) {
        accountNumber = accNum;
        balance = bal;
        accountType = type;
    }
    public void deposit(double amount) {
        balance = balance + amount;
    }
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
        } else {
            System.out.println("Not enough money!");
        }
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double bal) {
        balance = bal;
    }
    public String toString() {
        return "Account: " + accountNumber + ", Balance: " + balance + ", Type: " + accountType;
    }
}
