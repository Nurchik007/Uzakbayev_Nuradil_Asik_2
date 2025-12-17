public class Customer {
    String customerId;
    String name;
    String email;
    BankAccount account;
    public Customer(String id, String n, String e, BankAccount acc) {
        customerId = id;
        name = n;
        email = e;
        account = acc;
    }
    public String getInfo() {
        return "Customer: " + name + " (" + email + ")";
    }
    public String toString() {
        return getInfo() + " -> " + account.toString();
    }
}
