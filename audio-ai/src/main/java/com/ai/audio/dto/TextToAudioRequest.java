package com.ai.audio.dto;

public class TextToAudioRequest {
    private String text;
    private String voice; // "alloy", "echo", "fable", "onyx", "nova", "shimmer"
    private String audioFormat; // "wav", "mp3", "pcm16"
    private Double speed; // 0.25 to 4.0
    
    public TextToAudioRequest() {
    }
    
    public TextToAudioRequest(String text, String voice, String audioFormat, Double speed) {
        this.text = text;
        this.voice = voice;
        this.audioFormat = audioFormat;
        this.speed = speed;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getVoice() {
        return voice;
    }
    
    public void setVoice(String voice) {
        this.voice = voice;
    }
    
    public String getAudioFormat() {
        return audioFormat;
    }
    
    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }
    
    public Double getSpeed() {
        return speed;
    }
    
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
