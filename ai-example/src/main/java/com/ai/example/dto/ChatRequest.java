package com.ai.example.dto;

public class ChatRequest {
    
    private String message;
    private Double temperature;
    private Integer maxTokens;
    
    public ChatRequest() {
    }
    
    public ChatRequest(String message) {
        this.message = message;
    }
    
    public ChatRequest(String message, Double temperature, Integer maxTokens) {
        this.message = message;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public Integer getMaxTokens() {
        return maxTokens;
    }
    
    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }
}
