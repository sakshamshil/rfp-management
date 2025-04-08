package rfp.AdminPanel.CategoryOperations;

import rfp.DbConnect;

import java.sql.*;
import java.util.Scanner;


public class CategoryFunctions {
    private Scanner input;
    private Connection connection;

    public CategoryFunctions(Scanner i) {
        input = i;
        try {
            this.connection = DbConnect.connect();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCategories() {
        try {
            String query = "Select * from categories";
            PreparedStatement s = connection.prepareStatement(query);
            ResultSet rs = s.executeQuery();

            if (!rs.next()) {
                System.out.println("No categories found.");
            } else {
                System.out.println("\n");
                System.out.printf("%-5s %-25s%n", "No.", "Category Name");
                System.out.println("-------------------------------");

                int count = 1;
                do {
                    System.out.printf("%-5d %-25s%n", count++, rs.getString("category"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
    public void addCategory() {
        try {
            while (true) {
                System.out.println("\nEnter the new category name : ");

                String category = input.nextLine();

                String query = "Select * from categories where category = ?";
                PreparedStatement s = connection.prepareStatement(query);
                s.setString(1, category);
                ResultSet rs = s.executeQuery();

                if (rs.next()) {
                    System.out.println("Category already exists. Enter a new category.");
                } else {
                    String query2 = "Insert into categories (category) Values (?)";
                    PreparedStatement s2 = connection.prepareStatement(query2);
                    s2.setString(1, category);
                    int affected = s2.executeUpdate();
                    System.out.println(affected > 0 ? "Category added successfully" : "Try again");
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
    public void removeCategory() {
        try {
            while (true) {
                System.out.println("\nEnter the category you want to delete : \nWarning : All vendors and RFPs related to this category will be deleted");

                String category = input.nextLine();

                String query = "Select * from categories where category = ?";
                PreparedStatement s = connection.prepareStatement(query);
                s.setString(1, category);
                ResultSet rs = s.executeQuery();

                if (!rs.next()) {
                    System.out.println("Category doesn't exists.");
                } else {
                    String query2 = "Delete from categories where category = ?";
                    PreparedStatement s2 = connection.prepareStatement(query2);
                    s2.setString(1, category);
                    int affected = s2.executeUpdate();
                    System.out.println(affected > 0 ? "Category added successfully" : "Try again");
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error -> " + e.getMessage());
        }
    }
}
