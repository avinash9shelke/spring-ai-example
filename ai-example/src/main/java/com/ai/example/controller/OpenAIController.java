package com.ai.example.controller;

import com.ai.example.dto.ChatRequest;
import com.ai.example.dto.ChatResponse;
import com.ai.example.dto.StreamChatRequest;
import com.ai.example.service.OpenAiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class OpenAIController {
    
    private final OpenAiService openAiService;
    
    public OpenAIController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }
    
    /**
     * Simple chat endpoint
     * POST /api/chat/simple
     * Body: { "message": "Your question here" }
     */
    @PostMapping("/simple")
    public ChatResponse simpleChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        return openAiService.chat(message);
    }
    
    /**
     * Chat with custom options (temperature, max tokens)
     * POST /api/chat/custom
     * Body: { "message": "Your question", "temperature": 0.7, "maxTokens": 1000 }
     */
    @PostMapping("/custom")
    public ChatResponse customChat(@RequestBody ChatRequest request) {
        return openAiService.chatWithOptions(request);
    }
    
    /**
     * Streaming chat endpoint - returns response as Server-Sent Events
     * POST /api/chat/stream
     * Body: { "message": "Your question here" }
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody StreamChatRequest request) {
        return openAiService.streamChat(request.getMessage());
    }
    
    /**
     * Chat with system prompt
     * POST /api/chat/system-prompt
     * Body: { "message": "User question", "systemPrompt": "You are a helpful assistant..." }
     */
    @PostMapping("/system-prompt")
    public ChatResponse chatWithSystemPrompt(@RequestBody StreamChatRequest request) {
        return openAiService.chatWithSystemPrompt(
                request.getMessage(), 
                request.getSystemPrompt()
        );
    }
    
    /**
     * Multi-turn conversation
     * POST /api/chat/conversation
     * Body: { "messages": ["First message", "Second message", "Third message"] }
     */
    @PostMapping("/conversation")
    public ChatResponse multiTurnChat(@RequestBody Map<String, List<String>> request) {
        List<String> messages = request.get("messages");
        return openAiService.multiTurnChat(messages);
    }
    
    /**
     * Health check endpoint
     * GET /api/chat/health
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "OpenAI Chat Service",
                "model", "gpt-5"
        );
    }
}
