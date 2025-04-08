package rfp.AdminPanel.RFPOperations;

import rfp.AdminPanel.AdminMenu;

import java.util.Scanner;

public class ManageRFPMenu {
    private Scanner input;

    public ManageRFPMenu(Scanner input) {
        this.input = input;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n\n    Manage RFPs");
            System.out.println("----------------------");
            System.out.println("\n1. Show list of open RFPs");
            System.out.println("2. Show list of closed RFPs");
            System.out.println("3. Create a new RFP");
            System.out.println("4. Show current quotes for a RFP");
            System.out.println("5. Close a RFP");
            System.out.println("6. Delete a RFP");
            System.out.println("7. Go back to Main Menu");
            System.out.println("\nEnter your choice : ");


            int which = input.nextInt();
            input.nextLine();

            switch (which) {
                case 1:
                    new RFPFunctions(input).listOpen();
                    break;
                case 2:
                    new RFPFunctions(input).listClosed();
                    break;
                case 3:
                    new RFPFunctions(input).createRFP();
                    break;
                case 4:
                    new RFPFunctions(input).showQuotes();
                    break;
                case 5:
                    new RFPFunctions(input).closeRFP();
                    break;
                case 6:
                    new RFPFunctions(input).deleteRFP();
                    break;
                case 7:
                    new AdminMenu(input).run();
                    return;
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }
}

