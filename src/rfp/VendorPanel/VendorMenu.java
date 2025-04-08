package rfp.VendorPanel;

import rfp.AdminPanel.RFPOperations.RFPFunctions;
import rfp.Auth.AuthenticationMenu;

import java.util.Scanner;

public class VendorMenu {
    private Scanner input;
    private int user_id;
    private String category;

    public VendorMenu(Scanner input, int user_id, String category) {
        this.input = input;
        this.user_id = user_id;
        this.category = category;
    }

    public void run() {
        while (true) {
            System.out.println("\n\n    Vendor Menu");
            System.out.println("----------------------");
            System.out.println("\n1. Show RFP List");
            System.out.println("2. Quote a RFP");
            System.out.println("3. Sign out");
            System.out.println("4. Exit");
            System.out.println("\nEnter your choice : ");

            int which = input.nextInt();
            input.nextLine();

            switch (which) {
                case 1:
                    new VendorActions(input).showRFP(category);
                    break;
                case 2:
                    new VendorActions(input).quote(user_id);
                    break;
                case 3:
                    new AuthenticationMenu(input).run();
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }
}
