package taxation;

import java.sql.*;
import java.util.Scanner;

public class Taxation {
    public static void main(String[] args) {
        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        int choice;
        boolean condition;

        do {
            System.out.println("========================================");
            System.out.println("            CUSTOM TAXATION             ");
            System.out.println("========================================");
            System.out.println("1. Register Tax Payer");
            System.out.println("2. Update Tax Payer");
            System.out.println("3. Search Tax Payer");
            System.out.println("4. Delete Tax Payer");
            System.out.println("5. Display All Tax Payers");
            System.out.println("0. Exit");
            System.out.println("----------------------------------------");
            System.out.print("Choose: ");
            choice = scanner.nextInt();

            // Handle user's choice
            switch (choice) {
                case 1:
                    registerTaxPayer(scanner);
                    break;
                case 2:
                    updateTaxPayer(scanner);
                    break;
                case 3:
                    searchTaxPayer(scanner);
                    break;
                case 4:
                    deleteTaxPayer(scanner);
                    break;
                case 5:
                    displayAllTaxPayers();
                    break;
                case 0:
                    System.out.println("Thank you for using the system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }

            // Ask user if they want to perform another operation
            System.out.print("Do you want to perform another operation? (yes/no): ");
            String userInput = scanner.next();
            condition = userInput.equalsIgnoreCase("yes");
        } while (condition);

        // Close scanner
        scanner.close();
    }

    private static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Taxation"; // Database URL
        String user = "@Guhirwa";   // Database username
        String password = "@Guhirwa9188@"; // Database password
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    // Method to register a tax payer
    private static void registerTaxPayer(Scanner scanner) {
        System.out.print("Enter names: ");
        String names = scanner.next();
        System.out.print("Enter TIN: ");
        String tin = scanner.next();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter your National ID: ");
        String NID = scanner.next();

        String sql = "INSERT INTO taxation_payer (name, tin, amount, NID) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, names);
            pstmt.setString(2, tin);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, NID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Data not inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a tax payer
    private static void updateTaxPayer(Scanner scanner) {
        System.out.print("Enter TIN of the tax payer to update: ");
        String tinToUpdate = scanner.next();
        System.out.print("Enter new amount: ");
        double newAmount = scanner.nextDouble();

        String sql = "UPDATE taxation_payer SET amount = ? WHERE tin = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newAmount);
            pstmt.setString(2, tinToUpdate);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tax payer updated successfully.");
            } else {
                System.out.println("No tax payer found with the provided TIN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search for a tax payer
    private static void searchTaxPayer(Scanner scanner) {
        System.out.print("Enter TIN of the tax payer to search: ");
        String tinToSearch = scanner.next();

        String sql = "SELECT * FROM taxation_payer WHERE tin = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tinToSearch);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("TIN: " + rs.getString("tin"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("National ID: " + rs.getString("NID"));
            } else {
                System.out.println("No tax payer found with the provided TIN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a tax payer
    private static void deleteTaxPayer(Scanner scanner) {
        System.out.print("Enter TIN of the tax payer to delete: ");
        String tinToDelete = scanner.next();

        String sql = "DELETE FROM taxation_payer WHERE tin = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tinToDelete);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tax payer deleted successfully.");
            } else {
                System.out.println("No tax payer found with the provided TIN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display all tax payers
    private static void displayAllTaxPayers() {
        String sql = "SELECT * FROM taxation_payer";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("List of all tax payers:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name") +
                        ", TIN: " + rs.getString("tin") +
                        ", Amount: " + rs.getDouble("amount") +
                        ", National ID: " + rs.getString("NID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}