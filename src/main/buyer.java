package main;

import config.config;
import java.util.*;

public class buyer {

    config con = new config();
    Scanner sc = new Scanner(System.in);

    public void createCommission(int buyerID) {
        System.out.println("\n=== CREATE COMMISSION ===");
        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        System.out.print("Enter Budget: ");
        double budget = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Deadline: ");
        String deadline = sc.next();

        String sql = "INSERT INTO Commission (buyerID, description, budget, deadline, status) VALUES (?, ?, ?, ?, 'Open')";
        con.addRecord(sql, buyerID, description, budget, deadline);
    }

    public void viewMyCommissions(int buyerID) {
        System.out.println("\n=== MY COMMISSIONS ===");
        String query = "SELECT * FROM Commission WHERE buyerID = ?";
        String[] headers = {"Commission ID", "Description", "Budget", "Deadline", "Status"};
        String[] columns = {"commissionID", "description", "budget", "deadline", "status"};
        con.viewRecords(query, headers, columns);
    }

    public void viewOffersForCommission(int commissionID) {
        System.out.println("\n=== OFFERS FOR COMMISSION " + commissionID + " ===");
        String query = "SELECT * FROM Offer WHERE commissionID = ?";
        String[] headers = {"Offer ID", "Artist ID", "Price", "Status"};
        String[] columns = {"offerID", "artistID", "price", "status"};
        con.viewRecords(query, headers, columns);
    }

    public void acceptOffer(int offerID) {
        String sql = "UPDATE Offer SET status = 'Accepted' WHERE offerID = ?";
        con.updateRecord(sql, offerID);
    }

    public void rejectOffer(int offerID) {
        String sql = "UPDATE Offer SET status = 'Rejected' WHERE offerID = ?";
        con.updateRecord(sql, offerID);
    }

    public void updateCommissionStatus(int commissionID, String status) {
        String sql = "UPDATE Commission SET status = ? WHERE commissionID = ?";
        con.updateRecord(sql, status, commissionID);
    }

    public void viewDeliveredArtwork(int buyerID) {
        System.out.println("\n=== DELIVERED ARTWORKS ===");
        String query = "SELECT a.artworkID, a.filePath, a.description, c.commissionID " +
                       "FROM Artwork a JOIN Commission c ON a.commissionID = c.commissionID " +
                       "WHERE c.buyerID = ?";
        String[] headers = {"Artwork ID", "File Path", "Description", "Commission ID"};
        String[] columns = {"artworkID", "filePath", "description", "commissionID"};
        con.viewRecords(query, headers, columns);
    }
}

