package rfp.AdminPanel.VendorOperations;

import rfp.AdminPanel.AdminMenu;

import java.util.Scanner;

public class ManageVendorsMenu {
    private Scanner input;

    public ManageVendorsMenu(Scanner input) {
        this.input = input;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n\n    Manage Vendors");
            System.out.println("----------------------");
            System.out.println("\n1. Show list of approved vendors");
            System.out.println("2. Show list of unapproved vendors");
            System.out.println("3. Approve a Vendor");
            System.out.println("4. Remove a Vendor");
            System.out.println("5. Go back to Main Menu");
            System.out.println("\nEnter your choice : ");


            int which = input.nextInt();
            input.nextLine();

            switch (which) {
                case 1:
                    new VendorFunctions(input).showApproved();
                    break;
                case 2:
                    new VendorFunctions(input).showUnapproved();
                    break;
                case 3:
                    new VendorFunctions(input).approve();
                    break;
                case 4:
                    new VendorFunctions(input).remove();
                    break;
                case 5:
                    new AdminMenu(input).run();
                    return;
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }
}

