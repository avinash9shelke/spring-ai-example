package com.ai.example.service;

import com.ai.example.dto.ChatRequest;
import com.ai.example.dto.AiChatResponse;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class OpenAiService {

    private static final String AI_MODEL = "gpt-5";

    private final ChatModel chatModel;
    
    public OpenAiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
    
    /**
     * Simple chat completion with default settings
     */
    public AiChatResponse chat(String message) {
        String response = chatModel.call(message);
        return new AiChatResponse(response, AI_MODEL, null);
    }
    
    /**
     * Chat completion with custom options
     */
    public AiChatResponse chatWithOptions(ChatRequest request) {
        OpenAiChatOptions.Builder optionsBuilder = OpenAiChatOptions.builder()
                .model(AI_MODEL);
        
        if (request.getTemperature() != null) {
            optionsBuilder.temperature(request.getTemperature());
        }
        
        if (request.getMaxTokens() != null) {
            optionsBuilder.maxTokens(request.getMaxTokens());
        }
        
        Prompt prompt = new Prompt(request.getMessage(), optionsBuilder.build());
        ChatResponse aiResponse = chatModel.call(prompt);
        
        String responseText = aiResponse.getResult().getOutput().getText();
        Integer tokensUsed = aiResponse.getMetadata().getUsage() != null ? 
                aiResponse.getMetadata().getUsage().getTotalTokens().intValue() : null;
        
        return new AiChatResponse(responseText, AI_MODEL, tokensUsed);
    }
    
    /**
     * Streaming chat completion
     */
    public Flux<String> streamChat(String message) {
        Prompt prompt = new Prompt(message);
        return chatModel.stream(prompt)
                .map(response -> response.getResult().getOutput().getText());
    }
    
    /**
     * Chat with system prompt
     */
    public AiChatResponse chatWithSystemPrompt(String userMessage, String systemPrompt) {
        String combinedMessage = "System: " + systemPrompt + "\n\nUser: " + userMessage;
        String response = chatModel.call(combinedMessage);
        return new AiChatResponse(response, AI_MODEL, null);
    }
    
    /**
     * Multi-turn conversation
     */
    public AiChatResponse multiTurnChat(List<String> messages) {
        StringBuilder conversation = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            conversation.append("Message ").append(i + 1).append(": ").append(messages.get(i)).append("\n");
        }
        
        String response = chatModel.call(conversation.toString());
        return new AiChatResponse(response, AI_MODEL, null);
    }
}
