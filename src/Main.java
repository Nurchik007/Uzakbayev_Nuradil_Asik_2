public class Main {
    public static void main(String[] args) {
        BankAccount acc1 = new BankAccount("111", 1000, "Savings");
        BankAccount acc2 = new BankAccount("222", 500, "Checking");
        Customer c1 = new Customer("C1", "Alice", "alice@mail.com", acc1);
        Customer c2 = new Customer("C2", "Bob", "bob@mail.com", acc2);
        Bank bank = new Bank("MyBank");
        bank.addCustomer(c1);
        bank.addCustomer(c2);
        bank.showCustomers();
        acc1.deposit(200);
        acc2.withdraw(100);
        acc1.setBalance(5000);
        System.out.println("After operation:");
        bank.showCustomers();
        if (acc1.getBalance() > acc2.getBalance()) {
            System.out.println(c1.getInfo() + " richer than " + c2.getInfo());
        } else {
            System.out.println(c2.getInfo() + " richer than " + c1.getInfo());
        }
    }
}
