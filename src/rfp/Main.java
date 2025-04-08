package rfp;

import rfp.Auth.AuthenticationMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("\n\n------------------------------------------------------");
        System.out.println("      Welcome to RFP Management System");
        System.out.println("------------------------------------------------------");

        AuthenticationMenu authentication = new AuthenticationMenu(input);

        authentication.run();
    }
}
