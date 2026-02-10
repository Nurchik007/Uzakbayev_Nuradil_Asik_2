import domain.builder.CustomerBuilder;
import domain.factory.AccountFactory;
import domain.accounts.Customer;
import domain.accounts.BankAccount;
import domain.accounts.Bank;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Builder
        Customer c1 = new CustomerBuilder().setId("1").setName("John").build();
        Customer c2 = new CustomerBuilder().setId("2").setName("Mary").build();

        bank.addCustomer(c1);
        bank.addCustomer(c2);

        // Factory
        BankAccount acc1 = AccountFactory.createAccount("savings", "A001", 1000, 0.05);
        BankAccount acc2 = AccountFactory.createAccount("checking", "A002", 500, 20);

        bank.addAccount(acc1);
        bank.addAccount(acc2);

        System.out.println("All accounts:");
        bank.sortAccountsByBalance().forEach(System.out::println);

        System.out.println("\nFilter accounts with balance >= 600:");
        bank.filterAccountsByBalance(600).forEach(System.out::println);

        System.out.println("\nMonthly update:");
        bank.monthlyUpdateAll();
        bank.sortAccountsByBalance().forEach(System.out::println);
    }
}