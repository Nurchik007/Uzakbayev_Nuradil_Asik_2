import java.sql.*;
import java.util.Scanner;

public class FullDatabaseMenu {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "20012007NNNn";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to PostgreSQL!");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1. Show customers");
                System.out.println("2. Add customer");
                System.out.println("3. Delete customer");
                System.out.println("4. Show accounts");
                System.out.println("5. Add account");
                System.out.println("6. Update account balance");
                System.out.println("7. Delete account");
                System.out.println("8. Link customer to account");
                System.out.println("9. Show customer accounts");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showCustomers(conn);
                    case 2 -> addCustomer(conn, scanner);
                    case 3 -> deleteCustomer(conn, scanner);
                    case 4 -> showAccounts(conn);
                    case 5 -> addAccount(conn, scanner);
                    case 6 -> updateBalance(conn, scanner);
                    case 7 -> deleteAccount(conn, scanner);
                    case 8 -> linkCustomerAccount(conn, scanner);
                    case 9 -> showCustomerAccounts(conn);
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== CUSTOMER METHODS =====
    private static void showCustomers(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
        while (rs.next()) {
            System.out.println(rs.getString("id") + " | " + rs.getString("name"));
        }
    }

    private static void addCustomer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (id, name) VALUES (?, ?)");
        ps.setString(1, id);
        ps.setString(2, name);
        ps.executeUpdate();
        System.out.println("Customer added!");
    }

    private static void deleteCustomer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID to delete: ");
        String id = scanner.nextLine();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM customer WHERE id = ?");
        ps.setString(1, id);
        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Customer deleted!");
        else System.out.println("Customer not found.");
    }

    // ===== ACCOUNT METHODS =====
    private static void showAccounts(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bankaccount");
        while (rs.next()) {
            System.out.println(rs.getString("account_number") +
                    " | Balance: " + rs.getDouble("balance") +
                    " | Type: " + rs.getString("type"));
        }
    }

    private static void addAccount(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter type (Savings/Checking): ");
        String type = scanner.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO bankaccount (account_number, balance, type) VALUES (?, ?, ?)");
        ps.setString(1, accNum);
        ps.setDouble(2, balance);
        ps.setString(3, type);
        ps.executeUpdate();
        System.out.println("Account added!");
    }

    private static void updateBalance(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter new balance: ");
        double balance = scanner.nextDouble();

        PreparedStatement ps = conn.prepareStatement(
                "UPDATE bankaccount SET balance = ? WHERE account_number = ?");
        ps.setDouble(1, balance);
        ps.setString(2, accNum);
        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Balance updated!");
        else System.out.println("Account not found.");
    }

    private static void deleteAccount(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter account number to delete: ");
        String accNum = scanner.nextLine();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM bankaccount WHERE account_number = ?");
        ps.setString(1, accNum);
        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Account deleted!");
        else System.out.println("Account not found.");
    }

    // ===== CUSTOMERACCOUNT METHODS =====
    private static void linkCustomerAccount(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO customeraccount (customer_id, account_number) VALUES (?, ?)");
        ps.setString(1, customerId);
        ps.setString(2, accNum);
        ps.executeUpdate();
        System.out.println("Customer linked to account!");
    }

    private static void showCustomerAccounts(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT c.name, ca.account_number FROM customeraccount ca " +
                        "JOIN customer c ON ca.customer_id = c.id");
        while (rs.next()) {
            System.out.println(rs.getString("name") + " -> Account: " + rs.getString("account_number"));
        }
    }
}
