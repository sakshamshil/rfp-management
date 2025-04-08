package rfp.Auth;

import java.util.Scanner;

public class AuthenticationMenu {
    private Scanner input;
    public AuthenticationMenu(Scanner input) {
        this.input = input;
    }

    public void run() {
        System.out.println("\n Welcome to Authentication Menu");
        System.out.println("----------------------");
        System.out.println("\n1. For Login \n2. For Signup \nAny other key to Exit");
        System.out.println("\nEnter your choice : ");
        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1) {
            new Login(input).login();
        }
        else if (choice == 2) {
            //signup
            System.out.println("1. Signup as Admin \n2. Signup as Vendor");
            int which = input.nextInt();
            input.nextLine();
            while (true) {
                if (which == 1) {
                    new AdminSignup(input).signup();
                    break;
                }
                else if (which == 2) {
                    new VendorSignup(input).signup();
                    break;
                }
                else {
                    System.out.println("Enter valid choice");
                }
            }
        }
        else {
            System.out.println("Exiting .... ");
            System.exit(0);
        }


    }
}

