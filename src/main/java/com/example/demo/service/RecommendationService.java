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


    public List<RecommendationKeyword> getKeywords(Long tagId) {
        return crudRecommendationKeywordRepository.findByEmotionTag_Id(tagId);
    }

    public List<Furniture> getFurniture(Long tagId) {
        List<FurnitureTag> furnitureTags = furnitureTagRepository.findByEmotionTag_Id(tagId);
        return furnitureTags.stream()
                .map(FurnitureTag::getFurniture)
                .collect(Collectors.toList());
    }

}