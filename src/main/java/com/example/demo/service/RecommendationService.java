package com.example.demo.service;

import com.example.demo.entity.Furniture;
import com.example.demo.entity.FurnitureTag;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.repository.FurnitureRepository;
import com.example.demo.repository.FurnitureTagRepository;
import com.example.demo.repository.RecommendationKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final FurnitureTagRepository furnitureTagRepository;
    private final RecommendationKeywordRepository recommendationKeywordRepository;
    private final FurnitureRepository furnitureRepository;

    public RecommendationService(
            FurnitureTagRepository furnitureTagRepository,
            RecommendationKeywordRepository recommendationKeywordRepository,
            FurnitureRepository furnitureRepository
    ) {
        this.furnitureTagRepository = furnitureTagRepository;
        this.furnitureRepository = furnitureRepository;
        this.recommendationKeywordRepository = recommendationKeywordRepository;
    }

    public List<RecommendationKeyword> getKeywords(Long tagId) {
        return recommendationKeywordRepository.findByEmotionTag_Id(tagId);
    }

    public List<Furniture> getFurniture(Long tagId) {
        List<FurnitureTag> furnitureTags = furnitureTagRepository.findByEmotionTag_Id(tagId);
        return furnitureTags.stream()
                .map(FurnitureTag::getFurniture)
                .collect(Collectors.toList());
    }
}
