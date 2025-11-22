package main;
import config.config;
import java.util.Scanner;

public class main {
    
     public static void checkAndCreateAdmin() {
         Scanner sc = new Scanner(System.in);
        config con = new config();
        double userCount = con.getSingleValue("SELECT COUNT(*) FROM User");
        int type;
        String status = "";
        

        if (userCount == 0.0) {
            System.out.println("\nNo users found in the system.");
            System.out.println("Let's set up the first Admin account!\n");

            System.out.print("Enter Admin name: ");
            String name = sc.next();

            System.out.print("Enter email: ");
            String email = sc.next();

            System.out.print("Enter password: ");
            String password = sc.next();
            String hashedPass = con.hashPassword(password);
             System.out.print("Enter Account Status (1 - Active / 2 - Unavailable): ");
             type = sc.nextInt();
             status = (type == 1) ? "Active" : "Unavailable";
           
            String role = "Admin";
            String portfolio = "";
            String budget = "";

            String registerQuery = "INSERT INTO User(name, email, role, portfolio, budget, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            con.addRecord(registerQuery, name, email, role, portfolio, budget, hashedPass, status);

            System.out.println("\nAdmin account created successfully!");
        } else {
            System.out.println("Existing users found. Proceeding to login...\n");
        }
    }

    
    public static void login() {
        users us = new users();
        Scanner sc = new Scanner(System.in);
        config con = new config();
                   System.out.print("Enter Username: ");
                   String username = sc.next();
                   System.out.print("Enter Password: ");
                   String pass = sc.next();
                   String hashedPass = con.hashPassword(pass);

                   while(true){
                       String qr = "SELECT * FROM User WHERE name = ? AND password = ?";
                       java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qr, username, hashedPass);
                       
                       if (result.isEmpty()) {
                           System.out.println("Login Failed! No such user exists.");
                           break;
                       } else {
                           java.util.Map<String, Object> user = result.get(0); // get the first (and only) row
                            String stat = user.get("status").toString();
                            String role = user.get("role").toString();
                            int userID = Integer.parseInt(user.get("userID").toString()); // <-- get the ID here

                            if (stat.equals("SUSPENDED")) {
                                System.out.println("Your account is SUSPENDED for Inappropriate Behavior.");
                                System.out.println("Contact admins for further inquiries.");
                                break;
                            } else if (stat.equals("TERMINATED")) {
                                System.out.println("Your account has been TERMINATED for Inappropriate Behavior.");
                                break;
                            } else {
                                System.out.println("LOGIN SUCCESSFUL");
                                if (role.equals("Admin")) {
                                    us.admin();
                                } else if (role.equals("Buyer")) {
                                    us.buyer(userID);  // pass the correct ID
                                } else if (role.equals("Artist")) {
                                    us.artist(userID); // pass the correct ID
                                }
                                break;
}

                                
                       }
                   }   
    }
    
   public static void register() {
    Scanner sc = new Scanner(System.in);
    config con = new config();

    String rl = "", pf = "", bd = "", username = "", email = "", password = "", status = "";
    int type;

    System.out.print("Enter Username: ");
    username = sc.next();
    System.out.print("Enter Email: ");
    email = sc.next();

    
    while (true) {
        String qr = "SELECT * FROM User WHERE name = ? AND email = ?";
        java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qr, username, email);

        if (!result.isEmpty()) {
            System.out.print("Email/Username already exists, Enter new Username: ");
            username = sc.next();
            System.out.print("Enter new Email: ");
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

    System.out.print("Enter User Role (1 - Artist / 2 - Buyer): ");
    int role = sc.nextInt();

    switch (role) {
        case 1:
            rl = "Artist";
            System.out.print("Enter Portfolio Link: ");
            pf = sc.next();
            break;
        case 2:
            rl = "Buyer";
            System.out.print("Enter Project Budget: ");
            bd = sc.next();
            break;
        default:
            System.out.println("Invalid role.");
            
            break;
    }

   String hashedPass = con.hashPassword(password);
   String SQLInsert = "INSERT INTO User(name, email, role, portfolio, budget, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    con.addRecord(SQLInsert, username, email, rl, pf, bd, hashedPass, status);
    System.out.println("User registered successfully!");
}

    
                
    
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        config con = new config();
       con.connectDB();
       int choice;
       char cont;
       
       do{
           checkAndCreateAdmin();
           System.out.println("==== MAIN MENU ====");
           System.out.println("1. Login");
           System.out.println("2. Register");
           System.out.println("3. Logout");
           System.out.println("4. Exit");
           System.out.print("Enter choice: ");
           choice = sc.nextInt();
           
           switch(choice) {
               
               
               case 1:
                   login();
                break;
                
               case 2:
                   register();
                break;
                
               case 3:
                break;
               
               case 4:
                   System.exit(0);
                break;
                
               default:
                   System.out.println("Invalid choice.");
           }
     System.out.print("Do you want to continue? (Y/N): ");
            cont = sc.next().charAt(0);

        } while (cont == 'Y' || cont == 'y');

        System.out.println("Thank you! Program ended.");
    }
}
    
    
