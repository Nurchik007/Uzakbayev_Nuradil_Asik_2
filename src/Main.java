public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Customer c1 = new Customer("1", "John");
        Customer c2 = new Customer("2", "Mary");

        bank.addCustomer(c1);
        bank.addCustomer(c2);

        BankAccount acc1 = new SavingsAccount("A001", 1000, 0.05);
        BankAccount acc2 = new CheckingAccount("A002", 500, 20);

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
