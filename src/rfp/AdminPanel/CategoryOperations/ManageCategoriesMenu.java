package rfp.AdminPanel.CategoryOperations;

import rfp.AdminPanel.AdminMenu;
import rfp.AdminPanel.VendorOperations.VendorFunctions;

import java.util.Scanner;

public class ManageCategoriesMenu {
    private Scanner input;

    public ManageCategoriesMenu(Scanner input) {
        this.input = input;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n\n    Manage Categories");
            System.out.println("----------------------");
            System.out.println("\n1. Show list of categories");
            System.out.println("2. Add a Category");
            System.out.println("3. Remove a Category");
            System.out.println("4. Go back to Main Menu");
            System.out.println("\nEnter your choice : ");


            int which = input.nextInt();
            input.nextLine();

            switch (which) {
                case 1:
                    new CategoryFunctions(input).showCategories();
                    break;
                case 2:
                    new CategoryFunctions(input).addCategory();
                    break;
                case 3:
                    new CategoryFunctions(input).removeCategory();
                    break;
                case 4:
                    new AdminMenu(input).run();
                    return;
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }
}
