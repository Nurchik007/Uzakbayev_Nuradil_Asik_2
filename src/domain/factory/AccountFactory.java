package domain.factory;

import domain.accounts.BankAccount;
import domain.accounts.SavingsAccount;
import domain.accounts.CheckingAccount;

public class AccountFactory {
    public static BankAccount createAccount(String type, String accountNumber, double balance, double param) {
        switch (type.toLowerCase()) {
            case "savings":
                return new SavingsAccount(accountNumber, balance, param); // param = interestRate
            case "checking":
                return new CheckingAccount(accountNumber, balance, param); // param = fee
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
