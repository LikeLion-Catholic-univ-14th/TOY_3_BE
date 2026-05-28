package com.example.demo.service;

import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.repository.EmotionTagRepository;
import com.example.demo.repository.RecommendationKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final EmotionTagRepository emotionTagRepository;
    private final RecommendationKeywordRepository recommendationKeywordRepository;

    public List<String> getRecommendations(String tagName) {

        EmotionTag tag = emotionTagRepository.findByName(tagName)
                .orElseThrow();

        List<RecommendationKeyword> recommendations =
                recommendationKeywordRepository.findByBaseTag(tag);

        return recommendations.stream()
                .map(r -> r.getRecommendedTag().getName())
                .toList();
    }
}