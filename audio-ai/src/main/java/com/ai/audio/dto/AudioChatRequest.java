package com.ai.audio.dto;

public class AudioChatRequest {
    private String message;
    private String audioFormat; // "wav", "mp3", "pcm16", "g711_ulaw", "g711_alaw"
    private String voice; // "alloy", "echo", "fable", "onyx", "nova", "shimmer"
    
    public AudioChatRequest() {
    }
    
    public AudioChatRequest(String message, String audioFormat, String voice) {
        this.message = message;
        this.audioFormat = audioFormat;
        this.voice = voice;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getAudioFormat() {
        return audioFormat;
    }
    
    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }
    
    public String getVoice() {
        return voice;
    }
    
    public void setVoice(String voice) {
        this.voice = voice;
    }
}
