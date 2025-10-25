package main;

import config.config;
import java.util.*;

public class users {
    
   public void admin() {
    Scanner sc = new Scanner(System.in);
    admin ad = new admin();

    boolean stayLoggedIn = true;

    while (stayLoggedIn) {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("1. Manage Admin and Users");
        System.out.println("2. View Offers");
        System.out.println("3. Check Commissions");
        System.out.println("4. Log Out");
        System.out.print("Pick an option: ");

        // Validate main menu input
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number (1–4).");
            sc.next();
            System.out.print("Pick an option: ");
        }

        int opt = sc.nextInt();

        switch (opt) {
            case 1:
                boolean manageUsers = true;
                while (manageUsers) {
                    System.out.println("\n--- Manage Admin and Users ---");
                    System.out.println("1. Register an Admin");
                    System.out.println("2. Update User Status");
                    System.out.println("3. Delete an Account");
                    System.out.println("4. View Users");
                    System.out.println("5. Back to Dashboard");
                    System.out.print("Select an option: ");

                    // Validate submenu input
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number (1–5).");
                        sc.next();
                        System.out.print("Select an option: ");
                    }

                    int option = sc.nextInt();

                    switch (option) {
                        case 1:
                            ad.addAdmin();
                            break;
                        case 2:
                            ad.updateUser();
                            break;
                        case 3:
                            ad.deleteUser();
                            break;
                        case 4:
                            ad.viewUser();
                            break;
                        case 5:
                            manageUsers = false; // Return to dashboard
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                            break;
                    }
                }
                break;

            case 2:
                ad.viewOffers();
                break;

            case 3:
                ad.checkCommissions();
                break;

            case 4:
                System.out.println("Logging out...");
                stayLoggedIn = false;
                break;

            default:
                System.out.println("Invalid option. Please enter 1–4.");
                break;
        }
    }

    System.out.println("You have logged out successfully.");
}

    
}
