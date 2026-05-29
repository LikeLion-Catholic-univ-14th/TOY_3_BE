package com.example.demo.service;

import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.Furniture;
import com.example.demo.entity.FurnitureTag;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    private final CrudFurnitureTagRepository furnitureTagRepository;
    private final CrudRecommendationKeywordRepository crudRecommendationKeywordRepository;
    private final CrudFurnitureRepository furnitureRepository;


    public List<RecommendationKeyword> getKeywords(String tagName) {
        EmotionTag tag= emotionTagRepository.findByName(tagName).orElseThrow();

        return crudRecommendationKeywordRepository.findByEmotionTag_Id(tag.getId());
    }

    public List<Furniture> getFurniture(List<String> tagNames) {
        List<Long> tagIds = tagNames.stream()
                .map(name -> emotionTagRepository.findByName(name).orElseThrow().getId())
                .toList();

        List<FurnitureTag> furnitureTags = furnitureTagRepository.findByEmotionTag_IdIn(tagIds);
        return furnitureTags.stream()
                .map(FurnitureTag::getFurniture)
                .distinct()
                .toList();
    }

}