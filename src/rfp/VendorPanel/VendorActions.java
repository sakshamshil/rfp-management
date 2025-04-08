package rfp.VendorPanel;

import rfp.AdminPanel.RFPOperations.RFPFunctions;
import rfp.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class VendorActions {
    private Scanner input;
    private Connection connection;

    public VendorActions(Scanner input) {
        this.input = input;
        try {
            this.connection = DbConnect.connect();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void quote(int vendor_id) {
        int rfp_id;

        try {
            while (true) {
                System.out.println("\nEnter RFP ID to bid on: ");
                rfp_id = input.nextInt();
                input.nextLine();


                String rfpQuery = "Select * from rfp where rfp_id = ?";
                PreparedStatement rfpStatement = connection.prepareStatement(rfpQuery);
                rfpStatement.setInt(1, rfp_id);

                ResultSet rfpRes = rfpStatement.executeQuery();

                String vQuery = "SELECT * FROM quotes WHERE rfp_id = ? AND vendor_id = ?";
                PreparedStatement vStatement = connection.prepareStatement(vQuery);
                vStatement.setInt(1, rfp_id);
                vStatement.setInt(2, vendor_id);


                ResultSet vRes = vStatement.executeQuery();
                boolean rfpExists = rfpRes.next();
                boolean vendorExists = vRes.next();

                if (!rfpExists) {
                    System.out.println("Enter a valid RFP ID!");
                } else if (vendorExists) {
                    System.out.println("You have already made a bid");
                    return;
                } else {
                    break;
                }
            }

                System.out.println("Enter your price : ");
                int vendor_price = input.nextInt();
                input.nextLine();
                System.out.println("Enter proposed quantity : ");
                int quantity = input.nextInt();
                input.nextLine();
                System.out.println("Enter total estimated cost : ");
                int total_cost = input.nextInt();
                input.nextLine();
                System.out.println("Enter item description (optional) : ");
                String item_description = input.nextLine();

                String query = "Insert INTO quotes (rfp_id, vendor_id, vendor_price, quantity, total_cost, item_description) values (?, ?, ?, ?, ?, ?)";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, rfp_id);
                statement.setInt(2, vendor_id);
                statement.setInt(3, vendor_price);
                statement.setInt(4, quantity);
                statement.setInt(5, total_cost);
                statement.setString(6, item_description);

                int affected = statement.executeUpdate();

                if (affected > 0) {
                    System.out.println("Bid placed successfully");
                }
                else {
                    System.out.println("Bid couldn't be placed, please try again!");
                }

        }
            catch (SQLException e) {
                    System.out.println("SQL Error -> " + e.getMessage());
                }
    }

    public void showRFP(String category) {
        try {
            String Query = "Select * from rfp where category = ?";
            PreparedStatement Statement = connection.prepareStatement(Query);
            Statement.setString(1, category);
            ResultSet res = Statement.executeQuery();
            RFPFunctions.displayRFP(res);
        }
        catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
}
