//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package com.chatapp;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        com.chatapp.Login login = new com.chatapp.Login();

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

        // Login Phase (only if registration was successful)
        if (registrationResult.equals("User registered successfully.")) {
            System.out.println("\n--- LOGIN ---");

            int loginAttempts = 0;
            boolean loggedIn = false;

            // Allow up to 3 login attempts (using a loop)
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
            }
        } else {
            System.out.println("\nRegistration failed. Please restart the application.");
        }

        System.out.println("\n=" .repeat(50));
        System.out.println("   THANK YOU FOR USING CHAT APP");
        System.out.println("=" .repeat(50));

        scanner.close();
    }
}