package rfp.Auth;

import rfp.DbConnect;

import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class AdminSignup {
    private Scanner input;

    public AdminSignup(Scanner input) {
        this.input = input;
    }

    public void signup() {
        int id = -1;
        String email, password, first_name, last_name;
        String role = "admin";
        while (true) {
            System.out.println("\nEnter valid E-Mail ID : ");
            email = input.nextLine();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

            if (email == null || !email.matches(emailRegex)) {
                System.out.println("Invalid email format. Try again.");
                continue;
            }
            System.out.println("Enter password : ");
            password = input.nextLine();
            System.out.println("Enter your first name : ");
            first_name = input.nextLine();
            System.out.println("Enter your last name : ");
            last_name = input.nextLine();

            //CHECK IF EMAIL ALREADY EXISTS
            try {
                Connection connection = DbConnect.connect();
                String query = "Select * from user where email = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);

                ResultSet res = statement.executeQuery();

                if (res.next()) {
                    System.out.println("Email already exists. Go to login page.");
                }
                else{
                    //INSERT THE NEW DETAILS

                    //Insert to user table

                    String userQuery = "Insert INTO user (email, password, role) VALUES (?, ?, ?)";
                    PreparedStatement userStatement = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
                    userStatement.setString(1, email);
                    userStatement.setString(2, password);
                    userStatement.setString(3, role);

                    int userEffected = userStatement.executeUpdate();

                    ResultSet res2 = userStatement.getGeneratedKeys();

                    if (res2.next()) {
                        id = res2.getInt(1);
                    }

                    //Insert into admin table
                    String adminQuery = "Insert INTO admin (user_id, first_name, last_name) VALUES (?, ?, ?)";
                    PreparedStatement adminStatement = connection.prepareStatement(adminQuery);
                    adminStatement.setInt(1, id);
                    adminStatement.setString(2, first_name);
                    adminStatement.setString(3, last_name);

                    int adminEffected = adminStatement.executeUpdate();
                    if(adminEffected>0) {
                        System.out.println("Admin data inserted successfully!");
                        System.out.println("\nPlease login again with your new credentials");
                        new Login(input).login();
                        break;
                    }
                    else {
                        System.out.println("Error! Admin data couldn't be inserted. Please try again!");
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("SQL ERROR THROWN -> " + e.getMessage());
                return;
            }
        }
    }
}
