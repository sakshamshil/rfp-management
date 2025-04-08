package rfp.AdminPanel.VendorOperations;
import rfp.DbConnect;

import java.sql.*;
import java.util.Scanner;

public class VendorFunctions {
    private Scanner input;
    private Connection connection;

    public VendorFunctions(Scanner input) {
        this.input = input;
        try {
            this.connection = DbConnect.connect();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void showApproved() {
        try {
            String Query = "Select * from vendor where approval = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setString(1, "yes");
            ResultSet res = Statement.executeQuery();
            displayVendors(res);
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
    public void showUnapproved() {
        try {
            String Query = "Select * from vendor where approval = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setString(1, "no");
            ResultSet res = Statement.executeQuery();
            displayVendors(res);
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public void displayVendors(ResultSet rs) {
        try {
            if (!rs.next()) {
                System.out.println("\nNo vendor records found.");
            } else {
                System.out.println("\n");
                System.out.printf("%-8s %-12s %-12s %-10s %-14s %-15s %-15s %-12s %n",
                        "ID", "First Name", "Last Name", "Revenue", "Employees", "GST No", "Phone No", "Category");
                System.out.println("---------------------------------------------------------------------------------------------------------------");

                do {
                    System.out.printf("%-8d %-12s %-12s %-10.2f %-14d %-15s %-15s %-12s %n",
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDouble("revenue"),
                            rs.getInt("no_employees"),
                            rs.getString("gst_no"),
                            rs.getString("phone_no"),
                            rs.getString("category"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public void approve() {
        System.out.println("\nEnter the Vendor ID you want to approve :");
        int id = input.nextInt();
        input.nextLine();
        try {
            String Query = "Update vendor SET approval = ? where user_id = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setString(1, "yes");
            Statement.setInt(2, id);
            int affected = Statement.executeUpdate();
            if (affected > 0) {
                System.out.println("Vendor " + id + " approved");
                new ManageVendorsMenu(input).showMenu();
            }
            else {
                System.out.println("There are no vendors with ID : " + id);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public void remove() {
        System.out.println("\nEnter the Vendor ID you want to remove :");
        int id = input.nextInt();
        input.nextLine();
        try {
            String Query = "Delete from user where user_id = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setInt(1, id);
            int affected = Statement.executeUpdate();
            if (affected > 0) {
                System.out.println("Vendor " + id + " deleted");
                new ManageVendorsMenu(input).showMenu();
            }
            else {
                System.out.println("There are no vendors with ID : " + id);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
}
