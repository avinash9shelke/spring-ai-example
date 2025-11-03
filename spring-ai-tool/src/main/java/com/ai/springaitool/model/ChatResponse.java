package com.ai.springaitool.model;

/**
 * @author avinash
 */
public record ChatResponse(String response, boolean usedTools, String toolsUsed) {
}
