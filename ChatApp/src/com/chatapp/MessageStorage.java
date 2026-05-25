package com.chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageStorage {
    private static final String STORAGE_FILE = "messages.json";
    private Gson gson;
    
    public MessageStorage() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    // Store a single message in JSON file
    public void saveMessageToJson(Message message) {
        List<MessageData> allMessages = loadAllMessages();
        
        MessageData data = new MessageData(
            message.getMessageId(),
            message.getMessageNumber(),
            message.getRecipient(),
            message.getMessageContent(),
            message.getMessageHash(),
            message.isSent(),
            message.isStored(),
            message.isDisregarded()
        );
        
        allMessages.add(data);
        
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(allMessages, writer);
            System.out.println("Message stored in JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }
    
    // Load all messages from JSON file
    public List<MessageData> loadAllMessages() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (java.io.FileReader reader = new java.io.FileReader(file)) {
            MessageData[] messages = gson.fromJson(reader, MessageData[].class);
            if (messages != null) {
                return java.util.Arrays.asList(messages);
            }
        } catch (IOException e) {
            System.out.println("Error loading messages from JSON: " + e.getMessage());
        }
        
        return new ArrayList<>();
    }
    
    // Inner class for JSON serialization
    private static class MessageData {
        String messageId;
        int messageNumber;
        String recipient;
        String messageContent;
        String messageHash;
        boolean sent;
        boolean stored;
        boolean disregarded;
        
        public MessageData(String messageId, int messageNumber, String recipient, 
                          String messageContent, String messageHash, boolean sent, 
                          boolean stored, boolean disregarded) {
            this.messageId = messageId;
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.messageContent = messageContent;
            this.messageHash = messageHash;
            this.sent = sent;
            this.stored = stored;
            this.disregarded = disregarded;
        }
    }
}
