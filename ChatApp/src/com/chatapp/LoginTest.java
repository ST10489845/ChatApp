package com.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


class LoginTest {

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }


    @Test
    void testUsernameCorrectlyFormatted_AssertEquals() {
        // Test Data: "kyl_1"
        String result = login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("User registered successfully.", result);
    }

    @Test
    void testUsernameIncorrectlyFormatted_AssertEquals() {
        // Test Data: "kyle!!!!!!!"
        String result = login.registerUser("kyle!!!!!!!", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }



    @Test
    void testPasswordMeetsComplexity_AssertEquals() {
        // Test Data: "Ch&sec@ke99!"
        String result = login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("User registered successfully.", result);
    }

    @Test
    void testPasswordDoesNotMeetComplexity_AssertEquals() {
        // Test Data: "password"
        String result = login.registerUser("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }



    @Test
    void testCellPhoneCorrectlyFormatted_AssertEquals() {
        // Test Data: +27838968976
        String result = login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("User registered successfully.", result);
    }

    @Test
    void testCellPhoneIncorrectlyFormatted_AssertEquals() {
        // Test Data: 08966553
        String result = login.registerUser("kyl_1", "Ch&sec@ke99!", "08966553", "Kyle", "Smith");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }


    @Test
    void testLoginSuccessful_AssertTrue() {
        login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(login.loginUser("kyl_1", "Ch&sec@ke99!"));
    }

    @Test
    void testLoginFailed_AssertFalse() {
        login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(login.loginUser("wrong", "wrong"));
    }

    @Test
    void testUsernameCorrectlyFormatted_AssertTrue() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void testUsernameIncorrectlyFormatted_AssertFalse() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void testPasswordMeetsComplexity_AssertTrue() {
        assertTrue(login.checkPasswordComplexity("Ch&sec@ke99!"));
    }

    @Test
    void testPasswordDoesNotMeetComplexity_AssertFalse() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void testCellPhoneCorrectlyFormatted_AssertTrue() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void testCellPhoneIncorrectlyFormatted_AssertFalse() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}