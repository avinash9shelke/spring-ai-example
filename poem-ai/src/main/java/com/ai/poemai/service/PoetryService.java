package com.ai.poemai.service;

import com.ai.poemai.record.PoemRecord;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * @author avinash
 */
@Service
public class PoetryService {

    private final ChatClient chatClient;
    String system_prompt = """
               "You are a poem agent who write poem's on input genre - #genre  and theme - #theme."
            """;

    PoetryService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public PoemRecord generate(final String genre, final String theme) {
        return chatClient
                .prompt(system_prompt.replace("#genre", genre).replace("#theme", theme))
                .call()
                .entity(PoemRecord.class);
    }
}
