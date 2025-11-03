package com.ai.spring.controller;

import com.ai.spring.service.EmbeddingsService;
import java.util.List;
import java.util.Map;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author avinash
 */
@RestController
public class EmbeddingsController {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingsService embeddingsService;

    public EmbeddingsController(EmbeddingModel embeddingModel, EmbeddingsService embeddingsService) {
        this.embeddingModel = embeddingModel;
        this.embeddingsService = embeddingsService;
    }

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        var embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }

    @GetMapping("/ai/embedding/similarity")
    public ResponseEntity<EmbeddingResponse> similarity(@RequestParam(value = "messages", defaultValue = "Tell me a joke") List<String> message) {
        var embeddingResponse = this.embeddingsService.similarity(message);
        return ResponseEntity.ok(embeddingResponse);
    }
}
