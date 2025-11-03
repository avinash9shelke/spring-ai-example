package com.ai.chatmemory.model;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author avinash
 */
@Data
public class ChatRequest {
    @NotNull
    private String prompt;
}
