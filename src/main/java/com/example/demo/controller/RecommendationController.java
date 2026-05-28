package com.example.demo.controller;

import com.example.demo.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/{tagName}")
    public List<String> getRecommendations(@PathVariable String tagName) {
        return recommendationService.getRecommendations(tagName);
    }
}
