package com.chatapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageManager {
    private List<Message> messages;
    private int totalMessagesSent;
    private MessageStorage storage;
    private Scanner scanner;

    public MessageManager() {
        this.messages = new ArrayList<>();
        this.totalMessagesSent = 0;
        this.storage = new MessageStorage();
        this.scanner = new Scanner(System.in);
    }

    // Create a new message with user input
    public Message createMessage(int messageNumber) {
        System.out.println("\n--- Message #" + messageNumber + " ---");
        
        // Get recipient
        System.out.print("Enter recipient cell number (e.g., +27718693002): ");
        String recipient = scanner.nextLine();
        
        String recipientCheck = Message.checkRecipientCell(recipient);
        while (recipientCheck.contains("incorrectly formatted")) {
            System.out.println(recipientCheck);
            System.out.print("Please enter a valid cell number: ");
            recipient = scanner.nextLine();
            recipientCheck = Message.checkRecipientCell(recipient);
        }
        System.out.println(recipientCheck);
        
        // Get message content
        System.out.print("Enter your message (max 250 characters): ");
        String content = scanner.nextLine();
        
        String lengthCheck = Message.checkMessageLength(content);
        while (lengthCheck.contains("exceeds")) {
            System.out.println(lengthCheck);
            System.out.print("Please enter a shorter message: ");
            content = scanner.nextLine();
            lengthCheck = Message.checkMessageLength(content);
        }
        System.out.println(lengthCheck);
        
        return new Message(messageNumber, recipient, content);
    }

    // Get user choice for message action
    public String getMessageAction(Message message) {
        System.out.println("\nChoose an option:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Enter choice (1-3): ");
        
        String choice = scanner.nextLine();
        String result = "";
        
        switch (choice) {
            case "1":
                result = message.sendMessage();
                totalMessagesSent++;
                storage.saveMessageToJson(message);
                break;
            case "2":
                result = message.disregardMessage();
                break;
            case "3":
                result = message.storeMessage();
                storage.saveMessageToJson(message);
                break;
            default:
                result = "Invalid choice. Message not processed.";
        }
        
        return result;
    }

    // Process a single message from creation to completion
    public boolean processMessage(int messageNumber) {
        Message message = createMessage(messageNumber);
        
        // Display message details
        System.out.println("\n--- Message Details ---");
        System.out.println(message.printMessageDetails());
        
        // Get action choice
        String actionResult = getMessageAction(message);
        System.out.println(actionResult);
        
        // Add to list
        messages.add(message);
        
        return message.isSent() || message.isStored();
    }

    // Display all sent/recent messages
    public String displayRecentMessages() {
        if (messages.isEmpty()) {
            return "No messages have been created yet.";
        }
        
        StringBuilder output = new StringBuilder();
        output.append("\n=== Recent Messages ===\n");
        
        for (Message msg : messages) {
            if (msg.isSent() || msg.isStored()) {
                output.append(msg.printMessageDetails()).append("\n");
                output.append("Status: ").append(msg.isSent() ? "SENT" : "STORED").append("\n");
                output.append("---\n");
            }
        }
        
        return output.toString();
    }

    // Get total number of messages sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Get all messages
    public List<Message> getAllMessages() {
        return messages;
    }
}
