package com.ai.chatmemory.controller;

import com.ai.chatmemory.model.ChatRequest;
import com.ai.chatmemory.service.ChatMemoryService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author avinash
 */
@RestController
public class ChatMemoryController {

    private final ChatMemoryService chatMemoryService;

    public ChatMemoryController(ChatMemoryService chatMemoryService) {
        this.chatMemoryService = chatMemoryService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody @Valid ChatRequest request) {
        String response = chatMemoryService.chat(request.getPrompt());
        return ResponseEntity.ok(response);
    }
}
