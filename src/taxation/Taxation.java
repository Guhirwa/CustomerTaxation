package taxation;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;

public class Taxation {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        int choice;
        String userInput;
        boolean condition;
        String names = "";
        String tin = "";
        double amount = 0;
        String NID = "";

        try {

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

                switch (choice) {
                    case 1:
                        System.out.print("Enter names: ");
                        names = scanner.next();
                        System.out.print("Enter tin: ");
                        tin = scanner.next();
                        System.out.print("Enter amount: ");
                        amount = scanner.nextDouble();
                        System.out.print("Enter your National ID: ");
                        NID = scanner.next();
                        break;
                    case 2:
                        System.out.println("Option 2 selected");
                        break;
                    case 3:
                        System.out.println("Option 3 selected");
                        break;
                    case 4:
                        System.out.println("Option 4 selected");
                        break;
                    case 5:
                        System.out.println("Option 5 selected");
                        break;
                    case 0:
                        System.out.println("Thank you for using the system");
//                    condition = false;
                        // We can exit the system without asking the user to say yes or no !!
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid or Wrong Choice !!!");
                        break;
                }

                System.out.print("Do you want to perform another operation? (yes/no): ");
                userInput = scanner.next();
                condition = userInput.equalsIgnoreCase("yes");
            } while (condition);

            String jdbc_url = "jdbc:mysql://localhost:3306/Taxation";
            String usr = "@Guhirwa";
            String pswd = "@Guhirwa9188@";

//            Step 1: Load and Register database Driver. This process in optional;

//            Step 2: Create Connection;
            Connection conn = DriverManager.getConnection(jdbc_url, usr, pswd);

//            Step 3: Create Statement;
            Statement statement = conn.createStatement();
            String sqlQuery = "INSERT INTO taxation_payer (name, tin, amount, NID) VALUES ('" + names +"', '" + tin + "', " + amount + ", '" + NID + "');";

//            Step 4: Execute Statement;
            int rowsAffected = statement.executeUpdate(sqlQuery);
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully");
            } else {
                System.out.println("Not Inserted");
            }

//            Step 5: Close Connection;
            conn.close();

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}