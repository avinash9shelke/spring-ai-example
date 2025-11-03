package com.ai.springaitool.controller;

import com.ai.springaitool.model.ChatRequest;
import com.ai.springaitool.model.ChatResponse;
import com.ai.springaitool.service.AIAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for AI Agent with tool calling capabilities
 * @author avinash
 */
@RestController
@RequestMapping("/api/agent")
public class AIAgentController {

    private final AIAgentService aiAgentService;

    public AIAgentController(AIAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    /**
     * Main chat endpoint - AI agent automatically decides which tools to use
     * 
     * Example requests:
     * - "What's the weather in London?"
     * - "Tell me about Albert Einstein"
     * - "What's the weather in Tokyo and give me information about Mount Fuji"
     * 
     * @param request Chat request with a user message
     * @return AI response with tool usage information
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        if (request.message() == null || request.message().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ChatResponse("Message cannot be empty", false, "None"));
        }
        
        ChatResponse response = aiAgentService.chat(request.message());
        return ResponseEntity.ok(response);
    }

    /**
     * Direct weather endpoint - bypasses AI and calls weather tool directly
     * 
     * @param location City or location name
     * @return Weather information
     */
    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String location) {
        if (location == null || location.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Location parameter is required");
        }
        
        String weather = aiAgentService.getWeather(location);
        return ResponseEntity.ok(weather);
    }

    /**
     * Direct Wikipedia endpoint - bypasses AI and calls Wikipedia tool directly
     * 
     * @param topic Topic to search for
     * @return Wikipedia article summary
     */
    @GetMapping("/wikipedia")
    public ResponseEntity<String> getWikipedia(@RequestParam String topic) {
        if (topic == null || topic.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Topic parameter is required");
        }
        
        String article = aiAgentService.getWikipediaArticle(topic);
        return ResponseEntity.ok(article);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Agent is running with Weather and Wikipedia tools enabled");
    }
}
