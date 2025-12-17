import java.util.ArrayList;
public class Bank {
    String bankName;
    ArrayList<Customer> customers = new ArrayList<>();
    public Bank(String name) {
        bankName = name;
    }
    public void addCustomer(Customer c) {
        customers.add(c);
    }
    public void showCustomers() {
        for (Customer c : customers) {
            System.out.println(c);
        }
    }
}
