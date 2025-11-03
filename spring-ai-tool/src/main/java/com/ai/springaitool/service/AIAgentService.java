package com.ai.springaitool.service;

import com.ai.springaitool.model.ChatResponse;
import com.ai.springaitool.tools.WeatherTool;
import com.ai.springaitool.tools.WikipediaTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

/**
 * AI Agent Service that orchestrates tool usage with Spring AI
 * @author avinash
 */
@Service
public class AIAgentService {

    private final ChatClient chatClient;
    private final WeatherTool weatherTool;
    private final WikipediaTool wikipediaTool;

    public AIAgentService(OpenAiChatModel chatModel, WeatherTool weatherTool, WikipediaTool wikipediaTool) {
        this.weatherTool = weatherTool;
        this.wikipediaTool = wikipediaTool;
        
        // Build ChatClient with function calling enabled
        this.chatClient = ChatClient.builder(chatModel)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(1.0)
                        .build())
                .defaultTools(weatherTool, wikipediaTool)
                .build();
    }

    /**
     * Process user message with an AI agent that can use tools
     */
    public ChatResponse chat(String userMessage) {
        try {
            // Call the AI with function calling enabled
            String response = chatClient.prompt()
                    .user(userMessage)
                    .call()
                    .content();

            // For now, return simple response
            // Tool usage detection can be enhanced with metadata inspection
            return new ChatResponse(
                    response, 
                    true, 
                    "Weather and Wikipedia tools available"
            );

        } catch (Exception e) {
            return new ChatResponse(
                    "Error processing request: " + e.getMessage(),
                    false,
                    "None"
            );
        }
    }

    /**
     * Get weather information for a location
     */
    public String getWeather(String location) {
        return weatherTool.getWeather(location);
    }

    /**
     * Get Wikipedia article summary
     */
    public String getWikipediaArticle(String topic) {
        return wikipediaTool.getArticle(topic);
    }
}
