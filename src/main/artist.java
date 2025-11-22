package main;

import config.config;
import java.util.*;

public class artist {

    config con = new config();
    Scanner sc = new Scanner(System.in);

    public void viewOpenCommissions() {
        System.out.println("\n=== OPEN COMMISSIONS ===");
        String query = "SELECT * FROM Commission WHERE status = 'Open'";
        String[] headers = {"Commission ID", "Buyer ID", "Description", "Budget", "Deadline", "Status"};
        String[] columns = {"commissionID", "buyerID", "description", "budget", "deadline", "status"};
        con.viewRecords(query, headers, columns);
    }

    public void submitOffer(int artistID) {
        System.out.println("\n=== SUBMIT OFFER ===");
        System.out.print("Enter Commission ID: ");
        int commissionID = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter your Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        String sql = "INSERT INTO Offer (commissionID, artistID, price, status) VALUES (?, ?, ?, 'Pending')";
        con.addRecord(sql, commissionID, artistID, price);
    }

    public void viewMyOffers(int artistID) {
        System.out.println("\n=== MY OFFERS ===");
        String query = "SELECT * FROM Offer WHERE artistID = ?";
        String[] headers = {"Offer ID", "Commission ID", "Price", "Status"};
        String[] columns = {"offerID", "commissionID", "price", "status"};
        con.viewRecords(query, headers, columns);
    }

    public void viewAcceptedCommissions(int artistID) {
        System.out.println("\n=== ACCEPTED COMMISSIONS ===");
        String query = "SELECT * FROM Offer WHERE artistID = ? AND status = 'Accepted'";
        String[] headers = {"Offer ID", "Commission ID", "Price", "Status"};
        String[] columns = {"offerID", "commissionID", "price", "status"};
        con.viewRecords(query, headers, columns);
    }

    public void uploadArtwork(int artistID) {
        System.out.println("\n=== UPLOAD ARTWORK ===");
        System.out.print("Enter Commission ID: ");
        int commissionID = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter file path for artwork: ");
        String filePath = sc.nextLine();

        System.out.print("Enter description (optional): ");
        String description = sc.nextLine();

        String sql = "INSERT INTO Artwork (commissionID, artistID, filePath, description) VALUES (?, ?, ?, ?)";
        con.addRecord(sql, commissionID, artistID, filePath, description);
    }

    public void viewMyArtworks(int artistID) {
        System.out.println("\n=== MY ARTWORKS ===");
        String query = "SELECT * FROM Artwork WHERE artistID = ?";
        String[] headers = {"Artwork ID", "Commission ID", "File Path", "Description"};
        String[] columns = {"artworkID", "commissionID", "filePath", "description"};
        con.viewRecords(query, headers, columns);
    }
}
