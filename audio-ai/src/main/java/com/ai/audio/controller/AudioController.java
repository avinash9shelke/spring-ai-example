package com.ai.audio.controller;

import com.ai.audio.dto.AudioChatRequest;
import com.ai.audio.dto.AudioChatResponse;
import com.ai.audio.dto.SimpleTextRequest;
import com.ai.audio.service.AudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/audio")
public class AudioController {
    
    private static final Logger logger = LoggerFactory.getLogger(AudioController.class);
    
    private final AudioService audioService;
    
    public AudioController(AudioService audioService) {
        this.audioService = audioService;
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "audio-ai");
        response.put("model", "gpt-4o-audio-preview");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Simple chat endpoint
     * POST /api/audio/chat/simple
     * Body: {"message": "Your question here"}
     */
    @PostMapping("/chat/simple")
    public ResponseEntity<Map<String, String>> simpleChat(@RequestBody SimpleTextRequest request) {
        logger.info("Received simple chat request");
        
        try {
            String response = audioService.simpleChat(request.getMessage());
            
            Map<String, String> result = new HashMap<>();
            result.put("response", response);
            result.put("model", "gpt-4o-audio-preview");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error processing simple chat", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Chat with audio output
     * POST /api/audio/chat/audio
     * Body: {"message": "Your question", "audioFormat": "mp3", "voice": "alloy"}
     */
    @PostMapping("/chat/audio")
    public ResponseEntity<AudioChatResponse> chatWithAudio(@RequestBody AudioChatRequest request) {
        logger.info("Received audio chat request");
        
        try {
            AudioChatResponse response = audioService.chatWithAudioOutput(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing audio chat", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Chat with custom options
     * POST /api/audio/chat/custom
     * Body: {"message": "Your question", "temperature": 0.8, "maxTokens": 500}
     */
    @PostMapping("/chat/custom")
    public ResponseEntity<Map<String, Object>> chatWithOptions(@RequestBody Map<String, Object> request) {
        logger.info("Received custom chat request");
        
        try {
            String message = (String) request.get("message");
            Double temperature = request.containsKey("temperature") ? 
                    ((Number) request.get("temperature")).doubleValue() : null;
            Integer maxTokens = request.containsKey("maxTokens") ? 
                    ((Number) request.get("maxTokens")).intValue() : null;
            
            String response = audioService.chatWithOptions(message, temperature, maxTokens);
            
            Map<String, Object> result = new HashMap<>();
            result.put("response", response);
            result.put("model", "gpt-4o-audio-preview");
            result.put("temperature", temperature);
            result.put("maxTokens", maxTokens);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error processing custom chat", e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Streaming chat endpoint
     * POST /api/audio/chat/stream
     * Body: {"message": "Your question"}
     * Returns: Server-Sent Events (SSE)
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody SimpleTextRequest request) {
        logger.info("Received streaming chat request");
        
        return audioService.streamChat(request.getMessage())
                .onErrorResume(e -> {
                    logger.error("Error in streaming chat", e);
                    return Flux.just("Error: " + e.getMessage());
                });
    }
    
    /**
     * Chat with system prompt
     * POST /api/audio/chat/system-prompt
     * Body: {"message": "Your question", "systemPrompt": "You are an expert..."}
     */
    @PostMapping("/chat/system-prompt")
    public ResponseEntity<Map<String, String>> chatWithSystemPrompt(@RequestBody Map<String, String> request) {
        logger.info("Received system prompt chat request");
        
        try {
            String message = request.get("message");
            String systemPrompt = request.get("systemPrompt");
            
            String response = audioService.chatWithSystemPrompt(message, systemPrompt);
            
            Map<String, String> result = new HashMap<>();
            result.put("response", response);
            result.put("model", "gpt-4o-audio-preview");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error processing system prompt chat", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Multi-turn conversation
     * POST /api/audio/chat/conversation
     * Body: {"messages": ["Message 1", "Message 2", "Message 3"]}
     */
    @PostMapping("/chat/conversation")
    public ResponseEntity<Map<String, Object>> multiTurnConversation(@RequestBody Map<String, List<String>> request) {
        logger.info("Received multi-turn conversation request");
        
        try {
            List<String> messages = request.get("messages");
            
            String response = audioService.multiTurnConversation(messages);
            
            Map<String, Object> result = new HashMap<>();
            result.put("response", response);
            result.put("model", "gpt-4o-audio-preview");
            result.put("messageCount", messages.size());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error processing multi-turn conversation", e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Get available voices for audio generation
     * GET /api/audio/voices
     */
    @GetMapping("/voices")
    public ResponseEntity<Map<String, Object>> getAvailableVoices() {
        Map<String, Object> response = new HashMap<>();
        response.put("voices", List.of("alloy", "echo", "fable", "onyx", "nova", "shimmer"));
        response.put("defaultVoice", "alloy");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get supported audio formats
     * GET /api/audio/formats
     */
    @GetMapping("/formats")
    public ResponseEntity<Map<String, Object>> getSupportedFormats() {
        Map<String, Object> response = new HashMap<>();
        response.put("formats", List.of("mp3", "wav", "pcm16", "g711_ulaw", "g711_alaw"));
        response.put("defaultFormat", "mp3");
        return ResponseEntity.ok(response);
    }
}
