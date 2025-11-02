package com.ai.audio.dto;

public class AudioTranscriptionRequest {
    private String audioBase64;
    private String audioFormat; // "mp3", "wav", "m4a", "webm", etc.
    private String language; // Optional: ISO-639-1 format (e.g., "en", "es")
    private String prompt; // Optional: guide the transcription
    
    public AudioTranscriptionRequest() {
    }
    
    public AudioTranscriptionRequest(String audioBase64, String audioFormat, String language, String prompt) {
        this.audioBase64 = audioBase64;
        this.audioFormat = audioFormat;
        this.language = language;
        this.prompt = prompt;
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
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getPrompt() {
        return prompt;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
