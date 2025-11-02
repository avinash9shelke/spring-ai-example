package com.ai.poemai.controller;

import com.ai.poemai.record.PoemRecord;
import com.ai.poemai.service.PoetryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author avinash
 */
@RestController
public class PoemController {

    private final PoetryService poetryService;

    public PoemController(PoetryService poetryService) {
        this.poetryService = poetryService;
    }

    @PostMapping("/poems")
    public ResponseEntity<PoemRecord> generate(@RequestBody PoemGenerationRequest request) {
        PoemRecord response = poetryService.generate(request.genre, request.theme);
        return ResponseEntity.ok(response);
    }

    record PoemGenerationRequest(String genre, String theme) {
    }
}
