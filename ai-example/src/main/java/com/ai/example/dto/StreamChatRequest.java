package com.ai.example.dto;

public class StreamChatRequest {
    
    private String message;
    private String systemPrompt;
    
    public StreamChatRequest() {
    }
    
    public StreamChatRequest(String message) {
        this.message = message;
    }
    
    public StreamChatRequest(String message, String systemPrompt) {
        this.message = message;
        this.systemPrompt = systemPrompt;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSystemPrompt() {
        return systemPrompt;
    }
    
    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }
}
