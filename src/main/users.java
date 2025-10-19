package main;

import config.config;
import java.util.*;

public class users {
    
    public void admin(){
        Scanner sc = new Scanner(System.in);
        admin ad = new admin();
        
        System.out.println("Welcome to the Admin Dashboard");
         System.out.println("1. Manage Admin and Users");
         System.out.println("2. Veiw Offers");
         System.out.println("3. Check Commissions");
         System.out.print("Pick an option: ");
         int opt = sc.nextInt();
         while (true) {
             if (opt > '2') {
                System.out.print("Invalid option. Try Again: ");
                opt = sc.nextInt();
            } else {
                break;
            }
         }
                                    
            switch (opt){
              case 1:
                   System.out.println("What would you like to do? ");
                   System.out.println("1. Register an Admin");
                   System.out.println("2. Update User Status");
                   System.out.println("3. Delete an account");
                   System.out.println("4. View Users");
                   
                   System.out.print("Select an option: ");
                   int option = sc.nextInt();
                   
                   while (true){
                       if (option > '4') {
                           System.out.print("Invalid option, Try Again: ");
                           option = sc.nextInt();
                       } else {
                           break;
                       }
                   }
                   
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
                   }
                   
              break; 
              
              case 2:
                  ad.viewOffers();
              
              break;
              
              case 3: 
              break;
    }
            
    }
    
}
