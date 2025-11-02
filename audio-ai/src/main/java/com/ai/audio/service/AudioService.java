package com.ai.audio.service;

import com.ai.audio.dto.AudioChatRequest;
import com.ai.audio.dto.AudioChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AudioService {
    
    private static final Logger logger = LoggerFactory.getLogger(AudioService.class);
    
    private final ChatModel chatModel;
    
    public AudioService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
    
    /**
     * Simple chat with gpt-4o-audio-preview model
     */
    public String simpleChat(String message) {
        logger.info("Processing simple chat request with message: {}", message);
        
        var options = OpenAiChatOptions.builder()
                .model("gpt-4o-audio-preview")
                .temperature(0.7)
                .build();
        
        var prompt = new Prompt(message, options);
        var response = chatModel.call(prompt);
        
        return response.getResult().getOutput().getText();
    }
    
    /**
     * Chat with audio output capabilities
     * Note: The gpt-4o-audio-preview model supports audio modalities
     */
    public AudioChatResponse chatWithAudioOutput(AudioChatRequest request) {
        logger.info("Processing audio chat request with message: {}", request.getMessage());
        
        // Configure audio output options
        Map<String, Object> audioConfig = new HashMap<>();
        audioConfig.put("voice", request.getVoice() != null ? request.getVoice() : "alloy");
        audioConfig.put("format", request.getAudioFormat() != null ? request.getAudioFormat() : "mp3");
        
        var options = OpenAiChatOptions.builder()
                .model("gpt-4o-audio-preview")
                .temperature(0.7)
                .build();
        
        var prompt = new Prompt(request.getMessage(), options);
        var response = chatModel.call(prompt);
        
        String textResponse = response.getResult().getOutput().getText();
        
        // For demonstration, we'll return the text response
        // In a real implementation, you would extract audio data from the response
        // if the model returns it in the metadata or additional fields
        
        return new AudioChatResponse(
                textResponse,
                null, // Audio data would be extracted from response metadata
                request.getAudioFormat(),
                "gpt-4o-audio-preview"
        );
    }
    
    /**
     * Chat with custom options (temperature, max tokens)
     */
    public String chatWithOptions(String message, Double temperature, Integer maxTokens) {
        logger.info("Processing chat with custom options - temp: {}, maxTokens: {}", temperature, maxTokens);
        
        var optionsBuilder = OpenAiChatOptions.builder()
                .model("gpt-4o-audio-preview");
        
        if (temperature != null) {
            optionsBuilder.temperature(temperature);
        }
        
        if (maxTokens != null) {
            optionsBuilder.maxTokens(maxTokens);
        }
        
        var options = optionsBuilder.build();
        var prompt = new Prompt(message, options);
        var response = chatModel.call(prompt);
        
        return response.getResult().getOutput().getText();
    }
    
    /**
     * Streaming chat response
     */
    public Flux<String> streamChat(String message) {
        logger.info("Processing streaming chat request");
        
        var options = OpenAiChatOptions.builder()
                .model("gpt-4o-audio-preview")
                .temperature(0.7)
                .build();
        
        var prompt = new Prompt(message, options);
        
        return chatModel.stream(prompt)
                .map(chatResponse -> {
                    var content = chatResponse.getResult().getOutput().getText();
                    return content != null ? content : "";
                });
    }
    
    /**
     * Chat with system prompt for role-based responses
     */
    public String chatWithSystemPrompt(String message, String systemPrompt) {
        logger.info("Processing chat with system prompt");
        
        // Combine system prompt with user message
        String combinedMessage = systemPrompt + "\n\nUser: " + message;
        
        var options = OpenAiChatOptions.builder()
                .model("gpt-4o-audio-preview")
                .temperature(0.7)
                .build();
        
        var prompt = new Prompt(combinedMessage, options);
        var response = chatModel.call(prompt);
        
        return response.getResult().getOutput().getText();
    }
    
    /**
     * Multi-turn conversation
     */
    public String multiTurnConversation(java.util.List<String> messages) {
        logger.info("Processing multi-turn conversation with {} messages", messages.size());
        
        StringBuilder conversation = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            conversation.append("Message ").append(i + 1).append(": ").append(messages.get(i)).append("\n");
        }
        
        var options = OpenAiChatOptions.builder()
                .model("gpt-4o-audio-preview")
                .temperature(0.7)
                .build();
        
        var prompt = new Prompt(conversation.toString(), options);
        var response = chatModel.call(prompt);
        
        return response.getResult().getOutput().getText();
    }
}
