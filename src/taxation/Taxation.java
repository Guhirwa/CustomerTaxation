package taxation;

import java.sql.*;
import java.util.Scanner;

public class Taxation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }

            System.out.print("Do you want to perform another operation? (yes/no): ");
            condition = scanner.nextLine().equalsIgnoreCase("yes");
        } while (condition);
    }

    private static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Taxation";
        String user = "@Guhirwa";
        String password = "@Guhirwa9188@";
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    private static void registerTaxPayer(Scanner scanner) {
        System.out.print("Enter names: ");
        String names = scanner.nextLine();
        System.out.print("Enter TIN: ");
        String tin = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.next();
        System.out.print("Enter your National ID: ");
        String NID = scanner.nextLine();

        String sql = "INSERT INTO TaxPayer(name, tin, amount, NID) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, names);
            pstmt.setString(2, tin);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, NID);

            if (pstmt.executeUpdate() > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Data not inserted.");
            }
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
        }
    }

    private static void updateTaxPayer(Scanner scanner) {
        System.out.print("Enter TIN of the tax payer to update: ");
        String tinToUpdate = scanner.nextLine();

        // Search and display details of the tax payer
        String sql = "SELECT * FROM TaxPayer WHERE tin = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tinToUpdate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("TIN: " + rs.getString("tin"));
                System.out.println("Amount: " + rs.getDouble("amount"));
                System.out.println("National ID: " + rs.getString("NID"));

                System.out.println("Are you sure you want to update the This tax payer? (yes/no)");
                boolean choice = scanner.nextLine().equalsIgnoreCase("yes");

                if(choice){
                    System.out.println("Which field would you like to update?");
                    System.out.println("1. Name");
                    System.out.println("2. Amount");
                    System.out.println("3. National ID");
                    System.out.print("Choose an option (1-3): ");
                    int updateChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    // Prepare SQL for update based on user's choice
                    String updateSql = "";
                    switch (updateChoice) {
                        case 1:
                            System.out.print("Enter the new name: ");
                            String newName = scanner.nextLine();
                            updateSql = "UPDATE TaxPayer SET name = ? WHERE tin = ?";
                            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                                updatePstmt.setString(1, newName);
                                updatePstmt.setString(2, tinToUpdate);
                                int rowsAffected = updatePstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Tax payer name updated successfully.");
                                } else {
                                    System.out.println("Failed to update tax payer name.");
                                }
                            }
                            break;
                        case 2:
                            System.out.print("Enter the new amount (must be positive): ");
                            double newAmount = scanner.nextDouble();
                            scanner.nextLine();  // Consume newline
                            if (newAmount < 0) {
                                System.out.println("Amount must be a positive value.");
                                return;
                            }
                            updateSql = "UPDATE TaxPayer SET amount = ? WHERE tin = ?";
                            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                                updatePstmt.setDouble(1, newAmount);
                                updatePstmt.setString(2, tinToUpdate);
                                int rowsAffected = updatePstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Tax payer amount updated successfully.");
                                } else {
                                    System.out.println("Failed to update tax payer amount.");
                                }
                            }
                            break;
                        case 3:
                            System.out.print("Enter the new National ID: ");
                            String newNID = scanner.nextLine();
                            updateSql = "UPDATE TaxPayer SET NID = ? WHERE tin = ?";
                            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                                updatePstmt.setString(1, newNID);
                                updatePstmt.setString(2, tinToUpdate);
                                int rowsAffected = updatePstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Tax payer National ID updated successfully.");
                                } else {
                                    System.out.println("Failed to update tax payer National ID.");
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid choice! Please choose a valid field.");
                            break;
                    }
                }
            } else {
                System.out.println("No tax payer found with the provided TIN.");
            }
        } catch (SQLException e) {
            System.err.println("Error during search: " + e.getMessage());
        }
    }

    private static void searchTaxPayer(Scanner scanner) {
        System.out.print("Enter TIN of the tax payer to search: ");
        String tinToSearch = scanner.nextLine();

        String sql = "SELECT * FROM TaxPayer WHERE tin = ?";
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
            System.err.println("Error during search: " + e.getMessage());
        }
    }

    private static void deleteTaxPayer(Scanner scanner) {
        System.out.print("Enter TIN of the tax payer to delete: ");
        String tinToDelete = scanner.nextLine();

        String sql = "DELETE FROM TaxPayer WHERE tin = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tinToDelete);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Tax payer deleted successfully.");
            } else {
                System.out.println("No tax payer found with the provided TIN.");
            }
        } catch (SQLException e) {
            System.err.println("Error during deletion: " + e.getMessage());
        }
    }

    private static void displayAllTaxPayers() {
        String sql = "SELECT * FROM TaxPayer";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) { // Checks if ResultSet has any rows
                System.out.println("There are no tax payers in the database.");
                return;
            } else if (rs.isBeforeFirst()) {
                System.out.println("List of all tax payers:");
                System.out.println("-----------------------");
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("name") +
                            "\nTIN: " + rs.getString("tin") +
                            "\nAmount: " + rs.getDouble("amount") +
                            "\nNational ID: " + rs.getString("NID") + "\n");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error displaying tax payers: " + e.getMessage());
        }
    }

}