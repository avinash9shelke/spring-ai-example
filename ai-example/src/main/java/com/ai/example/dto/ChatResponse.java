package com.ai.example.dto;

public class ChatResponse {
    
    private String response;
    private String model;
    private Integer tokensUsed;
    
    public ChatResponse() {
    }
    
    public ChatResponse(String response) {
        this.response = response;
    }
    
    public ChatResponse(String response, String model, Integer tokensUsed) {
        this.response = response;
        this.model = model;
        this.tokensUsed = tokensUsed;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Integer getTokensUsed() {
        return tokensUsed;
    }
    
    public void setTokensUsed(Integer tokensUsed) {
        this.tokensUsed = tokensUsed;
    }
}
