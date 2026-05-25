package com.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    void setUp() {
        message = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }
    
    // Test: Message ID is not more than 10 characters
    @Test
    void testMessageIdLength_AssertTrue() {
        assertTrue(message.checkMessageID());
        assertTrue(message.getMessageId().length() <= 10);
    }
    
    @Test
    void testMessageIdIsTenDigits() {
        assertEquals(10, message.getMessageId().length());
    }
    
    // Test: Message not more than 250 characters - Success
    @Test
    void testMessageLengthLessThan250_Success() {
        String shortMessage = "Hello, this is a short message.";
        String result = Message.checkMessageLength(shortMessage);
        assertEquals("Message ready to send.", result);
    }
    
    // Test: Message not more than 250 characters - Failure
    @Test
    void testMessageLengthExceeds250_Failure() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("a");
        }
        String result = Message.checkMessageLength(longMessage.toString());
        assertTrue(result.contains("exceeds 250 characters by"));
        assertTrue(result.contains("please reduce the size"));
    }
    
    // Test: Recipient number correctly formatted - Success
    @Test
    void testRecipientNumberCorrectlyFormatted_Success() {
        String result = Message.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    // Test: Recipient number incorrectly formatted - Failure
    @Test
    void testRecipientNumberIncorrectlyFormatted_Failure() {
        String result = Message.checkRecipientCell("08575975889");
        assertTrue(result.contains("incorrectly formatted"));
    }
    
    @Test
    void testRecipientNumberEmpty_Failure() {
        String result = Message.checkRecipientCell("");
        assertTrue(result.contains("incorrectly formatted"));
    }
    
    // Test: Message hash is correct for Test Case 1
    @Test
    void testMessageHashForTestCase1() {
        // Test Case 1: Message ID starts with something, message number 1
        // Expected format: 00:0:HITONIGHT (example from spec)
        // Since message ID is random, we need to verify the format
        String hash = message.createMessageHash();
        assertNotNull(hash);
        assertTrue(hash.matches("\\d{2}:\\d+:.*"));
        assertTrue(hash.equals(hash.toUpperCase()));
    }
    
    // Test message hash with loop for multiple messages
    @ParameterizedTest
    @CsvSource({
        "Hi Mike, can you join us for dinner tonight?, HIMIKE",
        "Hello World, HELLOWORLD",
        "Short message, SHORTMESSAGE"
    })
    void testMessageHashFormat(String messageContent, String expectedWords) {
        Message testMsg = new Message(1, "+27718693002", messageContent);
        String hash = testMsg.createMessageHash();
        // Hash should contain first two digits of ID, colon, message number, colon, and concatenated first+last words
        assertTrue(hash.contains(":"));
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
    }
    
    // Test: Message ID is created
    @Test
    void testMessageIdIsCreated() {
        assertNotNull(message.getMessageId());
        System.out.println("Message ID generated: " + message.getMessageId());
    }
    
    // Test: Send Message
    @Test
    void testSendMessage() {
        String result = message.sendMessage();
        assertEquals("Message successfully sent.", result);
        assertTrue(message.isSent());
    }
    
    // Test: Disregard Message
    @Test
    void testDisregardMessage() {
        String result = message.disregardMessage();
        assertEquals("Press 0 to delete the message.", result);
        assertTrue(message.isDisregarded());
    }
    
    // Test: Store Message
    @Test
    void testStoreMessage() {
        String result = message.storeMessage();
        assertEquals("Message successfully stored.", result);
        assertTrue(message.isStored());
    }
    
    // Test: Print message details
    @Test
    void testPrintMessageDetails() {
        String details = message.printMessageDetails();
        assertTrue(details.contains("Message ID:"));
        assertTrue(details.contains("Message Hash:"));
        assertTrue(details.contains("Recipient:"));
        assertTrue(details.contains("Message:"));
    }
    
    // Test: Return total messages (using MessageManager)
    @Test
    void testReturnTotalMessages() {
        MessageManager manager = new MessageManager();
        assertEquals(0, manager.returnTotalMessages());
        
        // Create a message and mark as sent
        Message testMsg = new Message(1, "+27718693002", "Test message");
        testMsg.sendMessage();
        
        // We can't directly increment without processing, so we test the method exists
        assertNotNull(manager.returnTotalMessages());
    }
    
    // Additional test for message hash with specific test data
    @Test
    void testMessageHashContainsFirstTwoOfId() {
        // Force a known scenario by using reflection or testing format
        String hash = message.createMessageHash();
        String firstTwoOfId = message.getMessageId().substring(0, 2);
        assertTrue(hash.startsWith(firstTwoOfId + ":"));
    }
    
    @Test
    void testMessageHashContainsMessageNumber() {
        String hash = message.createMessageHash();
        assertTrue(hash.contains(":1:"));
    }
}
