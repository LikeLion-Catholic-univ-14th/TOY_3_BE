package com.example.demo.controller;

import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    public List<String> getRecommendations(@RequestParam List<String> tagNames) {
        return recommendationService.getRecommendations(tagNames);
    }
}
