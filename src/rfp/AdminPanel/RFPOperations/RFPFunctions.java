package rfp.AdminPanel.RFPOperations;

import rfp.AdminPanel.CategoryOperations.CategoryFunctions;
import rfp.DbConnect;

import java.sql.*;
import java.util.Scanner;

import static java.sql.Date.valueOf;

public class RFPFunctions {
    private Scanner input;
    private Connection connection;

    public RFPFunctions(Scanner input) {
        this.input = input;
        try {
            this.connection = DbConnect.connect();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void listOpen() {
        try {
            String Query = "Select * from rfp where status = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setString(1, "open");
            ResultSet res = Statement.executeQuery();
            displayRFP(res);
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
    public void listClosed() {
        try {
            String Query = "Select * from rfp where status = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setString(1, "close");
            ResultSet res = Statement.executeQuery();
            displayRFP(res);
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public static void displayRFP(ResultSet rs) {
        try {
            if (!rs.next()) {
                System.out.println("No RFP records found.");
            } else {
                System.out.println("\n");
                System.out.printf("%-8s %-30s %-12s %-14s %-14s %-10s%n",
                        "RFP ID", "Title", "Last Date", "Min Amount", "Max Amount", "Category");
                System.out.println("--------------------------------------------------------------------------------------------");

                do {
                    System.out.printf("%-8d %-30s %-12s %-14d %-14d %-10s%n",
                            rs.getInt("rfp_id"),
                            rs.getString("title"),
                            rs.getDate("last_date").toString(),
                            rs.getInt("min_amount"),
                            rs.getInt("max_amount"),
                            rs.getString("category"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public void createRFP() {
        System.out.print("\nEnter title: ");
        String title = input.nextLine();
        System.out.print("Enter last date (yyyy-mm-dd): ");
        String date = input.nextLine();
        System.out.print("Enter minimum amount: ");
        int minAmount = input.nextInt();
        System.out.print("Enter maximum amount: ");
        int maxAmount = input.nextInt();
        input.nextLine();
        String category;
        try {
            new CategoryFunctions(input).showCategories();

            while (true) {
                System.out.println("\nChoose a category from the above : ");

                category = input.nextLine();

                String query = "Select * from categories where category = ?";
                PreparedStatement s = connection.prepareStatement(query);
                s.setString(1, category);
                ResultSet rs = s.executeQuery();

                if (!rs.next()) {
                    System.out.println(category + " Category doesn't exist. Choose a valid category");
                } else {
                    break;
                }
            }
            String query = "INSERT INTO rfp (title, last_date, min_amount, max_amount, category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, title);
            statement.setDate(2, valueOf(date));
            statement.setInt(3, minAmount);
            statement.setInt(4, maxAmount);
            statement.setString(5, category);

            int affected = statement.executeUpdate();
            if (affected > 0) {
                System.out.println("RFP Added successfully");
            }
            else {
                System.out.println("RFP couldn't be added. Please try again");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }

    }

    public void showQuotes() {
        try {
            System.out.println("\n Enter RFP ID to show its quotations : ");
            int id = input.nextInt();
            input.nextLine();
            String query = "SELECT q.rfp_id, CONCAT(v.first_name, ' ', v.last_name) AS vendor_name, " +
                    "q.vendor_price, q.quantity, q.total_cost, q.item_description " +
                    "FROM quotes q " +
                    "INNER JOIN vendor v ON q.vendor_id = v.user_id where q.rfp_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if(!rs.next()) {
                System.out.println("No quote records found.");
            }
            else {
                System.out.println("\n");
                System.out.printf("%-8s %-25s %-14s %-10s %-12s %-30s%n",
                        "RFP ID", "Vendor Name", "Vendor Price", "Quantity", "Total Cost", "Item Description");
                System.out.println("----------------------------------------------------------------------------------------------------------");

                do {
                    String vendorName = rs.getString("vendor_name");

                    System.out.printf("%-8d %-25s %-14d %-10d %-12d %-30s%n",
                            rs.getInt("rfp_id"),
                            vendorName,
                            rs.getInt("vendor_price"),
                            rs.getInt("quantity"),
                            rs.getInt("total_cost"),
                            rs.getString("item_description"));
                } while (rs.next());
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public void closeRFP() {
        try {
            System.out.println("\n Enter RFP ID you want to close : ");
            int id = input.nextInt();
            input.nextLine();
            String query = "UPDATE rfp set status = ? where rfp_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "close");
            statement.setInt(2, id);

            int affected = statement.executeUpdate();
            if (affected > 0) {
                System.out.println("RFP " + id + " closed successfully");
            }
            else {
                System.out.println("RFP " + id + " couldn't be closed. Please try again");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

    public void deleteRFP(){
        try {
            System.out.println("\n Enter RFP ID you want to remove : ");
            int id = input.nextInt();
            input.nextLine();
            String query = "Delete from rfp where rfp_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int affected = statement.executeUpdate();
            if (affected > 0) {
                System.out.println("RFP " + id + " deleted successfully");
            }
            else {
                System.out.println("RFP " + id + " couldn't be deleted. Please try again");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }

}
