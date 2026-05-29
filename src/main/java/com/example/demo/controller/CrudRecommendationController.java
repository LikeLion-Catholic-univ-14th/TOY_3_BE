package com.example.demo.controller;

import com.example.demo.entity.Furniture;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrudRecommendationController {

    private final RecommendationService recommendationService;

    public CrudRecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
/*
    @GetMapping("/recommend/keywords")
    public List<RecommendationKeyword> getKeywords(
            @RequestParam String tagName
    ) {
        return recommendationService.getKeywords(tagName);
    }*/

    @GetMapping("/recommend/furniture")
    public List<Furniture> getFurniture(
            @RequestParam List<String> tagNames
    ) {
        return recommendationService.getFurniture(tagNames);
    }
}
