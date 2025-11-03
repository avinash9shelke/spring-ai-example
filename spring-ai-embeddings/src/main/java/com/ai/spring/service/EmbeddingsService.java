package com.ai.spring.service;

import java.util.List;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.stereotype.Service;

/**
 * @author avinash
 */
@Service
public class EmbeddingsService {

    private final MistralAiEmbeddingModel embeddingModel;

    public EmbeddingsService(MistralAiEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public EmbeddingResponse similarity(List<String> messages) {
        return this.embeddingModel
                .embedForResponse(messages);
    }
}
