package com.ai.spring.config;

import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.mistralai.MistralAiEmbeddingOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author avinash
 */
@Configuration
public class EmbeddingsConfiguration {

    @Value("${spring.ai.mistralai.api-key}")
    private String apiKey;

    @Bean
    public MistralAiEmbeddingModel mistralAiEmbeddingModel() {
        var mistralAiApi = new MistralAiApi(apiKey);
        var embeddingModel = new MistralAiEmbeddingModel(mistralAiApi,
                MistralAiEmbeddingOptions.builder()
                        .withModel("mistral-embed")
                        .withEncodingFormat("float")
                        .build());
        return embeddingModel;
    }
}
