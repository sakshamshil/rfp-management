package rfp.AdminPanel;

import rfp.AdminPanel.CategoryOperations.ManageCategoriesMenu;
import rfp.AdminPanel.VendorOperations.ManageVendorsMenu;
import rfp.AdminPanel.RFPOperations.ManageRFPMenu;
import rfp.Auth.AuthenticationMenu;

import java.util.Scanner;

public class AdminMenu {
    private Scanner input;

    public AdminMenu(Scanner input) {
        this.input = input;
    }

    public void run() {
        while (true) {
            System.out.println("\n\n    Admin Menu");
            System.out.println("----------------------");
            System.out.println("\n1. Manage Vendors");
            System.out.println("2. Manage RFPs");
            System.out.println("3. Manage Categories");
            System.out.println("4. Sign out");
            System.out.println("5. Exit");
            System.out.println("\nEnter your choice : ");

            int which = input.nextInt();
            input.nextLine();

            switch (which) {
                case 1:
                    new ManageVendorsMenu(input).showMenu();
                    break;
                case 2:
                    new ManageRFPMenu(input).showMenu();
                    break;
                case 3:
                    new ManageCategoriesMenu(input).showMenu();
                case 4:
                    new AuthenticationMenu(input).run();
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }
}
