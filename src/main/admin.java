package main;

import config.config;
import java.util.*;

public class admin {
    
    config con = new config();
    Scanner sc =  new Scanner(System.in);
    
    public void addAdmin() {

    String username, email, password, status;
    int type;

        System.out.println("\n=== ADD ADMIN ===");

    System.out.print("Enter Username: ");
    username = sc.next();
    System.out.print("Enter Email: ");
    email = sc.next();

    
    while (true) {
        String qr = "SELECT * FROM User WHERE name = ? OR email = ?";
        java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qr, username, email);

        if (!result.isEmpty()) {
            System.out.println("Email or Username already exists. Please enter different credentials.");
            System.out.print("Enter Username: ");
            username = sc.next();
            System.out.print("Enter Email: ");
            email = sc.next();
        } else {
            break;
        }
    }

    
    System.out.print("Enter Password: ");
    password = sc.next();

    System.out.print("Enter Account Status (1 - Active / 2 - Unavailable): ");
    type = sc.nextInt();
    status = (type == 1) ? "Active" : "Unavailable";

    String hashedPass = config.hashPassword(password); 

    String role = "Admin";
    String portfolio = "";
    String budget = "";

    String SQLAdmin = "INSERT INTO User(name, email, role, portfolio, budget, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    con.addRecord(SQLAdmin, username, email, role, portfolio, budget, hashedPass, status);

    System.out.println("Admin registered successfully!");
}
    
  public void updateUser() {
    
     String userQuery = "SELECT * FROM User";
     String[] userHeaders = {"User ID", "Name", "Email", "Role", "Portfolio (Artists)", "Budget(Commissioners)", "Status"};
     String[] userColumns = {"userID", "name", "email", "role", "portfolio", "budget", "status"};
     
     con.viewRecords(userQuery, userHeaders, userColumns);

    System.out.println("\n=== UPDATE USER STATUS ===");
    System.out.print("Enter Username to update: ");
    String username = sc.next().trim();

   
    double userCount = con.getSingleValue("SELECT COUNT(*) FROM User WHERE name = ?", username);
    if (userCount == 0.0) {
        System.out.println("⚠️ No user found with the name '" + username + "'.");
        return;
    }

 
    System.out.println("\nSelect new account status:");
    System.out.println("1. Active");
    System.out.println("2. Unavailable");
    System.out.println("3. Suspended");
    System.out.println("4. Terminated");

    int option;
    do {
        System.out.print("Enter option (1-4): ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number (1-4).");
            sc.next();
            System.out.print("Enter option (1-4): ");
        }
        option = sc.nextInt();
    } while (option < 1 || option > 4);

    
    String newStatus;
    switch (option) {
        case 1: newStatus = "Active"; break;
        case 2: newStatus = "Unavailable"; break;
        case 3: newStatus = "SUSPENDED"; break;
        case 4: newStatus = "TERMINATED"; break;
        default: return;
    }

    
    System.out.print("Are you sure you want to set '" + username + "' to " + newStatus + "? (Y/N): ");
    String confirm = sc.next();
    if (!confirm.equalsIgnoreCase("Y")) {
        System.out.println("Action canceled.");
        return;
    }

  
    String query = "UPDATE User SET status = ? WHERE name = ?";
    con.updateRecord(query, newStatus, username);

    System.out.println("User '" + username + "' has been updated to status: " + newStatus + ".");
}

  public void deleteUser() {
          System.out.println("\n=== DELETE USER ===");
          
     String userQuery = "SELECT * FROM User";
     String[] userHeaders = {"User ID", "Name", "Email", "Role", "Portfolio (Artists)", "Budget(Commissioners)", "Status"};
     String[] userColumns = {"userID", "name", "email", "role", "portfolio", "budget", "status"};
     
     con.viewRecords(userQuery, userHeaders, userColumns);

    System.out.print("Enter the username of the user to delete: ");
    String username = sc.next().trim();

   
    double userCount = con.getSingleValue("SELECT COUNT(*) FROM User WHERE name = ?", username);
    if (userCount == 0.0) {
        System.out.println("No user found with the name '" + username + "'.");
        return;
    }

    
    System.out.print("Are you sure you want to permanently delete the user '" + username + "'? (Y/N): ");
    String confirm = sc.next().trim();

    if (!confirm.equalsIgnoreCase("Y")) {
        System.out.println("Deletion canceled.");
        return;
    }

  
     String deleteQuery = "DELETE FROM User WHERE name = ?";
    con.deleteRecord(deleteQuery, username); 

    System.out.println("User '" + username + "' has been requested for deletion.");
    userCount = con.getSingleValue("SELECT COUNT(*) FROM User WHERE name = ?", username);
    if (userCount == 0.0) {
        System.out.println("Confirmed: user deleted.");
    } else {
        System.out.println("Warning: user still exists.");
    }
}
  
  public void viewUser() {
          System.out.println("\n=== VIEW USERS ===");

     String userQuery = "SELECT * FROM User";
     String[] userHeaders = {"User ID", "Name", "Email", "Role", "Portfolio (Artists)", "Budget(Commissioners)", "Status"};
     String[] userColumns = {"userID", "name", "email", "role", "portfolio", "budget", "status"};
     
     con.viewRecords(userQuery, userHeaders, userColumns);
  }
  
  public void viewOffers() {
      System.out.println("\n=== VIEW OFFERS ===");
      
    String offerQuery = "SELECT * FROM Offer";
    String[] offerHeaders = {"Offer ID", "Commission ID", "Artist ID", "Price", "Status"};
    String[] offerColumns = {"offerID", "commissionID", "artistID", "price", "status"};
    
    con.viewRecords(offerQuery, offerHeaders, offerColumns);
  }
  
  public void checkCommissions() {
      System.out.println("\n=== CHECK COMMISSIONS ===");
      
      String commissionsQuery = "SELECT * FROM Commission";
      String[] commissionsHeaders = {"Commission ID", "Commissioner ID", "Project Title", "Description", "Price", "Deadline", "Status"};
      String[] commissionsColumns = {"commissionID", "commissionerID", "title", "description", "price", "deadline", "status"};
      
      con.viewRecords(commissionsQuery, commissionsHeaders, commissionsColumns);
      
    
    System.out.print("\nEnter the Commission ID to review: ");
    while (!sc.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid Commission ID (number).");
        sc.next();
        System.out.print("Enter the Commission ID to review: ");
    }
    int commissionID = sc.nextInt();

    
    double commissionCount = con.getSingleValue("SELECT COUNT(*) FROM Commission WHERE commissionID = ?", commissionID);
    if (commissionCount == 0.0) {
        System.out.println("⚠️ No commission found with ID " + commissionID + ".");
        return;
    }

  
    System.out.println("\nSelect Action:");
    System.out.println("1. Approve Commission");
    System.out.println("2. Deny Commission");

    int option;
    do {
        System.out.print("Enter Option (1 or 2): ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number (1 or 2).");
            sc.next();
            System.out.print("Enter Option (1 or 2): ");
        }
        option = sc.nextInt();
    } while (option != 1 && option != 2);

    String newStatus = (option == 1) ? "APPROVED" : "DENIED";
    String action = (option == 1) ? "approve" : "deny";

    
    System.out.print("Are you sure you want to " + action + " Commission ID " + commissionID + "? (Y/N): ");
    String confirm = sc.next();
    if (!confirm.equalsIgnoreCase("Y")) {
        System.out.println("Action canceled.");
        return;
    }

    
    String updateQuery = "UPDATE Commission SET status = ? WHERE commissionID = ?";
    con.updateRecord(updateQuery, newStatus, commissionID);

    System.out.println(" Commission ID " + commissionID + " has been " + newStatus + ".");
  }
}  
