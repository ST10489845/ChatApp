package com.chatapp;

import java.util.Random;

public class Message {
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageContent;
    private String messageHash;
    private boolean sent;
    private boolean stored;
    private boolean disregarded;

    // Constructor
    public Message(int messageNumber, String recipient, String messageContent) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageId = generateMessageId();
        this.messageHash = createMessageHash();
        this.sent = false;
        this.stored = false;
        this.disregarded = false;
    }

    // Generate random 10-digit message ID
    private String generateMessageId() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    // Check if message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    // Check recipient cell number format
    public static String checkRecipientCell(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        // Check if starts with + and has international code (+27 for SA)
        if (phoneNumber.startsWith("+27") && phoneNumber.length() <= 13 && phoneNumber.length() >= 10) {
            // Check remaining characters are digits
            String remaining = phoneNumber.substring(1);
            if (remaining.matches("\\d+")) {
                return "Cell phone number successfully captured.";
            }
        }
        
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Create message hash: first two digits of ID:messageNumber:firstWord+lastWord
    public String createMessageHash() {
        String firstTwoDigits = messageId.substring(0, 2);
        String[] words = messageContent.trim().split("\\s+");
        
        String firstWord = "";
        String lastWord = "";
        
        if (words.length > 0) {
            firstWord = words[0];
            lastWord = words[words.length - 1];
        }
        
        String hash = firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Check message length
    public static String checkMessageLength(String message) {
        if (message == null) {
            return "Message exceeds 250 characters by 250; please reduce the size.";
        }
        
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // Send message method
    public String sendMessage() {
        this.sent = true;
        return "Message successfully sent.";
    }

    // Disregard message method
    public String disregardMessage() {
        this.disregarded = true;
        return "Press 0 to delete the message.";
    }

    // Store message method
    public String storeMessage() {
        this.stored = true;
        return "Message successfully stored.";
    }

    // Get message details for display
    public String printMessageDetails() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageContent;
    }

    // Getters
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
    public boolean isSent() { return sent; }
    public boolean isStored() { return stored; }
    public boolean isDisregarded() { return disregarded; }
}
