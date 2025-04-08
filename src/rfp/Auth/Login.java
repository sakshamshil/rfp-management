package rfp.Auth;

import java.util.Scanner;
import java.sql.*;

import rfp.AdminPanel.AdminMenu;
import rfp.DbConnect;
import rfp.VendorPanel.VendorMenu;

public class Login {
    private Scanner input;
    public Login(Scanner input) {
        this.input = input;
    }

    public void login () {
        String inputEmail, inputPass;
        while (true) {
            while (true) {
                System.out.println("\nEnter your E-Mail ID : ");
                inputEmail = input.nextLine();
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

                if (inputEmail != null && inputEmail.matches(emailRegex)) {
                    break;
                }
            }
            System.out.println("Enter your password : ");
            inputPass = input.nextLine();


            try {
                //CHECK IF EMAIL PASSWORD ARE CORRECT
                Connection connection = DbConnect.connect();
                String query = "Select * from user where email = ? and password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, inputEmail);
                statement.setString(2, inputPass);

                ResultSet res = statement.executeQuery();

                if (res.next()) {
                    int user_id = res.getInt("user_id");
                    String role = res.getString("role");
                    if(role.matches("admin")) {
                        new AdminMenu(input).run();
                    }
                    else if(role.matches("vendor")) {
                        String vendorQuery = "Select approval, category from vendor where user_id = ?";
                        PreparedStatement vendorStatement = connection.prepareStatement(vendorQuery);
                        vendorStatement.setInt(1, user_id);
                        ResultSet rs = vendorStatement.executeQuery();
                        if (rs.next()) {
                            String approval = rs.getString("approval");
                            String category = rs.getString("category");
                            if (approval.matches("no")) {
                                System.out.println("\nWait for approval by admin.");
                            }
                            else {
                                new VendorMenu(input, user_id, category).run();
                            }
                        }
                    }
                    break;
                }
                else{
                    System.out.println("The entered credentials does not match any user");
                }

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
