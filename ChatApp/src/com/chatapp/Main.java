package com.chatapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=" .repeat(50));
        System.out.println("   WELCOME TO CHAT APP REGISTRATION");
        System.out.println("=" .repeat(50));

        // Registration Phase
        System.out.println("\n--- REGISTRATION ---");

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Username (must contain '_' and be max 5 characters): ");
        String username = scanner.nextLine();

        System.out.print("Enter Password (min 8 chars, 1 capital, 1 number, 1 special): ");
        String password = scanner.nextLine();

        System.out.print("Enter Cell Phone Number (format: +27XXXXXXXXX): ");
        String phone = scanner.nextLine();

        // Attempt registration
        String registrationResult = login.registerUser(username, password, phone, firstName, lastName);
        System.out.println("\n" + registrationResult);

        boolean loggedIn = false;

        // Login Phase (only if registration was successful)
        if (registrationResult.equals("User registered successfully.")) {
            System.out.println("\n--- LOGIN ---");

            int loginAttempts = 0;

            // Allow up to 3 login attempts
            while (loginAttempts < 3 && !loggedIn) {
                System.out.print("Enter Username: ");
                String loginUsername = scanner.nextLine();

                System.out.print("Enter Password: ");
                String loginPassword = scanner.nextLine();

                loggedIn = login.loginUser(loginUsername, loginPassword);

                if (loggedIn) {
                    System.out.println("\n" + login.returnLoginStatus(true, firstName, lastName));
                } else {
                    loginAttempts++;
                    if (loginAttempts < 3) {
                        System.out.println(login.returnLoginStatus(false, null, null));
                        System.out.println("Attempts remaining: " + (3 - loginAttempts));
                    }
                }
            }

            if (!loggedIn) {
                System.out.println("\nToo many failed attempts. Please try again later.");
                System.out.println("\n=" .repeat(50));
                System.out.println("   THANK YOU FOR USING CHAT APP");
                System.out.println("=" .repeat(50));
                scanner.close();
                return;
            }
        } else {
            System.out.println("\nRegistration failed. Please restart the application.");
            scanner.close();
            return;
        }

        // ========== PART 2: QUICKCHAT APPLICATION ==========
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   WELCOME TO QUICKCHAT");
        System.out.println("=".repeat(50));

        // Ask how many messages the user wants to enter
        System.out.print("\nHow many messages would you like to send? ");
        int numMessages = Integer.parseInt(scanner.nextLine());
        
        MessageManager messageManager = new MessageManager();
        boolean running = true;
        
        while (running) {
            // Display menu
            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option (1-3): ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    // Send Messages - using a for loop for specified number of messages
                    System.out.println("\n--- SENDING MESSAGES ---");
                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\nProcessing message " + i + " of " + numMessages);
                        messageManager.processMessage(i);
                        System.out.println("-".repeat(30));
                    }
                    
                    // Display total number of messages sent
                    System.out.println("\n=== SUMMARY ===");
                    System.out.println("Total number of messages sent: " + messageManager.returnTotalMessages());
                    System.out.println("Total messages created: " + messageManager.getAllMessages().size());
                    break;
                    
                case "2":
                    // Show recently sent messages - feature in development
                    System.out.println("\nComing Soon.");
                    break;
                    
                case "3":
                    // Quit
                    System.out.println("\nThank you for using QuickChat!");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        System.out.println("\n=" .repeat(50));
        System.out.println("   THANK YOU FOR USING CHAT APP");
        System.out.println("=" .repeat(50));

        scanner.close();
    }
}
