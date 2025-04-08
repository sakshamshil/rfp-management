package rfp.Auth;

import rfp.AdminPanel.CategoryOperations.CategoryFunctions;
import rfp.DbConnect;

import java.sql.*;
import java.util.Scanner;

public class VendorSignup {
    private Scanner input;

    public VendorSignup(Scanner input) {
        this.input = input;
    }

    public void signup() {
        int id = -1, revenue, employees;
        String email, password, first_name, last_name, gst_no, phone_no, category;
        String role = "vendor";
        while (true) {
            System.out.println("\nEnter valid E-Mail ID : ");
            email = input.nextLine();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

            if (email == null || !email.matches(emailRegex)) {
                System.out.println("Invalid email format. Try again.");
                continue;
            }
            try {            //CHECK IF EMAIL ALREADY EXISTS
                Connection connection = DbConnect.connect();
                String query = "Select * from user where email = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);

                ResultSet res = statement.executeQuery();

                if (res.next()) {
                    System.out.println("Email already exists");
                }

                else{

                System.out.println("Enter password : ");
                password = input.nextLine();
                System.out.println("Enter your first name : ");
                first_name = input.nextLine();
                System.out.println("Enter your last name : ");
                last_name = input.nextLine();
                System.out.println("Enter your revenue : ");
                revenue = input.nextInt();
                input.nextLine();
                System.out.println("Enter number of employees: ");
                employees = input.nextInt();
                input.nextLine();
                System.out.println("Enter your GST Number : ");
                gst_no = input.nextLine();
                System.out.println("Enter your Phone Number : ");
                phone_no = input.nextLine();
                new CategoryFunctions(input).showCategories();

                    while (true) {
                        System.out.println("\nChoose a category from the above : ");

                        category = input.nextLine();

                        query = "Select * from categories where category = ?";
                        PreparedStatement s = connection.prepareStatement(query);
                        s.setString(1, category);
                        ResultSet rs = s.executeQuery();

                        if (!rs.next()) {
                            System.out.println(category + " Category doesn't exist. Choose a valid category");
                        } else {
                            break;
                        }
                    }

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

                    //Insert into vendor table
                    String vendorQuery = "Insert INTO vendor (user_id, first_name, last_name, revenue, no_employees, gst_no, phone_no, category) VALUES (?, ?, ?, ?,?, ?, ?, ?)";
                    PreparedStatement vendorStatement = connection.prepareStatement(vendorQuery);
                    vendorStatement.setInt(1, id);
                    vendorStatement.setString(2, first_name);
                    vendorStatement.setString(3, last_name);
                    vendorStatement.setInt(4, revenue);
                    vendorStatement.setInt(5, employees);
                    vendorStatement.setString(6, gst_no);
                    vendorStatement.setString(7, phone_no);
                    vendorStatement.setString(8, category);

                    int vendorEffected = vendorStatement.executeUpdate();
                    if(vendorEffected>0) {
                        System.out.println("Vendor data inserted successfully!");
                        System.out.println("\nPlease wait for admin to approve your request");
                        new AuthenticationMenu(input).run();
                        break;
                    }
                    else {
                        System.out.println("Error! vendor data couldn't be inserted. Please try again!");
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("SQL ERROR THROWN -> " + e.getMessage());
                return;
            }
        }
    }
}

