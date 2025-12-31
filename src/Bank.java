import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private List<Customer> customers = new ArrayList<>();
    private List<BankAccount> accounts = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public List<Customer> searchCustomerByName(String name) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<BankAccount> filterAccountsByBalance(double minBalance) {
        return accounts.stream()
                .filter(acc -> acc.getBalance() >= minBalance)
                .collect(Collectors.toList());
    }

    public List<BankAccount> sortAccountsByBalance() {
        return accounts.stream()
                .sorted(Comparator.comparingDouble(BankAccount::getBalance))
                .collect(Collectors.toList());
    }

    public void monthlyUpdateAll() {
        accounts.forEach(BankAccount::calculateMonthlyUpdate);
    }
}
