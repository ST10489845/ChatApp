package com.chatapp;

import java.util.regex.Pattern;


public class Login {

    // Stored user credentials
    private String registeredUsername;
    private String registeredPassword;
    private String registeredPhone;
    private String firstName;
    private String lastName;


    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }


    public boolean checkPasswordComplexity(String password) {
        Pattern capitalLetter = Pattern.compile("[A-Z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern specialChar = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

        return password.length() >= 8 &&
                capitalLetter.matcher(password).find() &&
                number.matcher(password).find() &&
                specialChar.matcher(password).find();
    }


    public boolean checkCellPhoneNumber(String phone) {
        // South African international format: +27 followed by 9 digits
        return Pattern.matches("\\+27[0-9]{9}", phone);
    }


    public String registerUser(String username, String password, String phone,
                               String firstName, String lastName) {
        // Check username format
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // Check password complexity
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        // Check phone number format
        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Store user details
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredPhone = phone;
        this.firstName = firstName;
        this.lastName = lastName;

        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return registeredUsername != null &&
                registeredUsername.equals(username) &&
                registeredPassword.equals(password);
    }


    public String returnLoginStatus(boolean isLoggedIn, String firstName, String lastName) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters for testing purposes
    public String getRegisteredUsername() { return registeredUsername; }
    public String getRegisteredPassword() { return registeredPassword; }
    public String getRegisteredPhone() { return registeredPhone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}