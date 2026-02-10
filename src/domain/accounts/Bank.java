package domain.accounts;
import java.util.*;
import java.util.stream.Collectors;

// ✅ Generics: универсальный репозиторий
class Repository<T> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public List<T> getAll() {
        return items;
    }

    public List<T> filter(java.util.function.Predicate<T> predicate) {
        return items.stream().filter(predicate).collect(Collectors.toList());
    }
}

// ✅ Дополнительная фича: Default interface methods
interface MonthlyUpdatePolicy {
    void apply(BankAccount account);

    default void logUpdate(BankAccount account) {
        System.out.println("Account " + account.getAccountNumber() +
                " updated. New balance: " + account.getBalance());
    }
}

public class Bank {
    private Repository<Customer> customers = new Repository<>();
    private Repository<BankAccount> accounts = new Repository<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    // ✅ Lambda Expressions + Streams
    public List<Customer> searchCustomerByName(String name) {
        return customers.filter(c -> c.getName().equalsIgnoreCase(name));
    }

    public List<BankAccount> filterAccountsByBalance(double minBalance) {
        return accounts.filter(acc -> acc.getBalance() >= minBalance);
    }

    public List<BankAccount> sortAccountsByBalance() {
        return accounts.getAll().stream()
                .sorted(Comparator.comparingDouble(BankAccount::getBalance))
                .collect(Collectors.toList());
    }

    public void monthlyUpdateAll() {
        accounts.getAll().forEach(acc -> {
            acc.calculateMonthlyUpdate();
            // вызов default метода
            acc.getUpdatePolicy().logUpdate(acc);
        });
    }
}
