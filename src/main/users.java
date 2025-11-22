package main;

import config.config;
import java.util.Scanner;

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

                        while (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number (1–5).");
                            sc.next();
                            System.out.print("Select an option: ");
                        }

                        int option = sc.nextInt();

                        switch (option) {
                            case 1: ad.addAdmin();
                            break;
                            case 2: ad.updateUser();
                            break;
                            case 3: ad.deleteUser();
                            break;
                            case 4: ad.viewUser();
                            break;
                            case 5: manageUsers = false;
                            break;
                            default: System.out.println("Invalid option. Try again.");
                            break;
                        }
                    }
                    break;
                case 2: ad.viewOffers();
                break;
                case 3: ad.checkCommissions();
                        break;
                case 4: {;
                    System.out.println("Logging out...");
                    stayLoggedIn = false;
                }
                break;
                default: System.out.println("Invalid option. Please enter 1–4.");
                break;
            }
        }

        System.out.println("You have logged out successfully.");
    }

    public void artist(int artistID) {
        Scanner sc = new Scanner(System.in);
        artist art = new artist();
        boolean stayLoggedIn = true;

        while (stayLoggedIn) {
            System.out.println("\n=== ARTIST DASHBOARD ===");
            System.out.println("1. View Open Commissions");
            System.out.println("2. Submit Offer");
            System.out.println("3. View My Offers");
            System.out.println("4. View Accepted Commissions");
            System.out.println("5. Upload Artwork");
            System.out.println("6. View My Artworks");
            System.out.println("7. Log Out");
            System.out.print("Pick an option: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number (1–7).");
                sc.next();
                System.out.print("Pick an option: ");
            }

            int opt = sc.nextInt();

            switch (opt) {
                case 1: art.viewOpenCommissions();
                break;
                case 2: art.submitOffer(artistID);
                break;
                case 3: art.viewMyOffers(artistID);
                break;
                case 4: art.viewAcceptedCommissions(artistID);
                break;
                case 5: art.uploadArtwork(artistID);
                break;
                case 6: art.viewMyArtworks(artistID);
                break;
                case 7: {
                    System.out.println("Logging out...");
                    stayLoggedIn = false;
                }
                break;
                default: System.out.println("Invalid option. Enter 1–7.");
                break;
            }
        }
    }

    public void buyer(int buyerID) {
        Scanner sc = new Scanner(System.in);
        buyer buy = new buyer();
        boolean stayLoggedIn = true;

        while (stayLoggedIn) {
            System.out.println("\n=== BUYER DASHBOARD ===");
            System.out.println("1. Create Commission");
            System.out.println("2. View My Commissions");
            System.out.println("3. View Offers for a Commission");
            System.out.println("4. Accept Offer");
            System.out.println("5. Reject Offer");
            System.out.println("6. Update Commission Status");
            System.out.println("7. View Delivered Artworks");
            System.out.println("8. Log Out");
            System.out.print("Pick an option: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number (1–8).");
                sc.next();
                System.out.print("Pick an option: ");
            }

            int opt = sc.nextInt();

            switch (opt) {
                case 1: buy.createCommission(buyerID);
                break;
                case 2: buy.viewMyCommissions(buyerID);
                break;
                case 3: {
                    System.out.print("Enter Commission ID: ");
                    int commissionID = sc.nextInt();
                    buy.viewOffersForCommission(commissionID);
                }
                break;
                case 4: {
                    System.out.print("Enter Offer ID to accept: ");
                    int offerID = sc.nextInt();
                    buy.acceptOffer(offerID);
                }
                break;
                case 5: {
                    System.out.print("Enter Offer ID to reject: ");
                    int offerID = sc.nextInt();
                    buy.rejectOffer(offerID);
                }
                break;
                case 6: {
                    System.out.print("Enter Commission ID: ");
                    int commissionID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new Status: ");
                    String status = sc.nextLine();
                    buy.updateCommissionStatus(commissionID, status);
                }
                break;
                case 7: buy.viewDeliveredArtwork(buyerID);
                break;
                case 8: {
                    System.out.println("Logging out...");
                    stayLoggedIn = false;
                }
                break;
                default: System.out.println("Invalid option. Enter 1–8.");
                break;
            }
        }
    }
}

