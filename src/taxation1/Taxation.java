package taxation1;

import java.sql.*;

public class Taxation {

    public static void main(String[] args) throws Exception {

        try{
//        Register the Driver Class
//        Establish Connection to the Database
            String jdbc_url = "jdbc:mysql://localhost:3306/Taxation";
            String username = "@Guhirwa";
            String password = "@Guhirwa9188@";
            Connection connection = DriverManager.getConnection(jdbc_url, username, password);
//        Create a Statement Object
            Statement statement = connection.createStatement();

//        Execute Queries
            String selectQuery = "SELECT * FROM Taxation";
            String updateQuery = "UPDATE Taxation SET column1 = value1  WHERE Condition";
            String insertQuery = "INSERT INTO Taxation_Payer(Name, NID, Salary, TIN) VALUES('GUHIRWA Christian', '76376735', 63725376, '84783687468')";

            int rowsAffected = statement.executeUpdate(insertQuery);
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully");
            } else {
                System.out.println("Not Inserted");
            }
//        Close Connection and close connection
        } catch (Exception e) {
            e.printStackTrace();
            }

    }
}
