import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.stream.Collectors;

public class BankRestApi {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "20012007NNNn";

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/accounts", new AccountsHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("REST API started on http://localhost:8080/accounts");
    }

    static class AccountsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");

            try {
                if ("GET".equalsIgnoreCase(method) && parts.length == 2) {
                    // GET /accounts
                    handleGetAll(exchange);
                } else if ("POST".equalsIgnoreCase(method)) {
                    // POST /accounts
                    handleCreate(exchange);
                } else if ("PUT".equalsIgnoreCase(method) && parts.length == 3) {
                    // PUT /accounts/{number}
                    handleUpdate(exchange, parts[2]);
                } else if ("DELETE".equalsIgnoreCase(method) && parts.length == 3) {
                    // DELETE /accounts/{number}
                    handleDelete(exchange, parts[2]);
                } else {
                    sendResponse(exchange, 404, "Not Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "Server Error: " + e.getMessage());
            }
        }

        private void handleGetAll(HttpExchange exchange) throws Exception {
            JSONArray accountsJson = new JSONArray();
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM bankaccount");
                while (rs.next()) {
                    JSONObject acc = new JSONObject();
                    acc.put("account_number", rs.getString("account_number"));
                    acc.put("balance", rs.getDouble("balance"));
                    acc.put("type", rs.getString("type"));
                    accountsJson.put(acc);
                }
            }
            sendResponse(exchange, 200, accountsJson.toString());
        }

        private void handleCreate(HttpExchange exchange) throws Exception {
            String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                    .lines().collect(Collectors.joining("\n"));
            JSONObject json = new JSONObject(body);

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO bankaccount (account_number, balance, type) VALUES (?, ?, ?)");
                ps.setString(1, json.getString("account_number"));
                ps.setDouble(2, json.getDouble("balance"));
                ps.setString(3, json.getString("type"));
                ps.executeUpdate();
            }
            sendResponse(exchange, 201, "Account created");
        }

        private void handleUpdate(HttpExchange exchange, String accNum) throws Exception {
            String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                    .lines().collect(Collectors.joining("\n"));
            JSONObject json = new JSONObject(body);

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE bankaccount SET balance = ? WHERE account_number = ?");
                ps.setDouble(1, json.getDouble("balance"));
                ps.setString(2, accNum);
                int rows = ps.executeUpdate();
                if (rows > 0) sendResponse(exchange, 200, "Balance updated");
                else sendResponse(exchange, 404, "Account not found");
            }
        }

        private void handleDelete(HttpExchange exchange, String accNum) throws Exception {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM bankaccount WHERE account_number = ?");
                ps.setString(1, accNum);
                int rows = ps.executeUpdate();
                if (rows > 0) sendResponse(exchange, 200, "Account deleted");
                else sendResponse(exchange, 404, "Account not found");
            }
        }

        private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
