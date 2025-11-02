package com.ai.audio.dto;

public class AudioChatResponse {
    private String textResponse;
    private String audioBase64;
    private String audioFormat;
    private String model;
    private long timestamp;
    
    public AudioChatResponse() {
    }
    
    public AudioChatResponse(String textResponse, String audioBase64, String audioFormat, String model) {
        this.textResponse = textResponse;
        this.audioBase64 = audioBase64;
        this.audioFormat = audioFormat;
        this.model = model;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getTextResponse() {
        return textResponse;
    }
    
    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }
    
    public String getAudioBase64() {
        return audioBase64;
    }
    
    public void setAudioBase64(String audioBase64) {
        this.audioBase64 = audioBase64;
    }
    
    public String getAudioFormat() {
        return audioFormat;
    }
    
    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
