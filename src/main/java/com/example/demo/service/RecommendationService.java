package com.example.demo.service;

import com.example.demo.entity.Furniture;
import com.example.demo.entity.FurnitureTag;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.repository.CrudFurnitureRepository;
import com.example.demo.repository.CrudFurnitureTagRepository;
import com.example.demo.repository.CrudRecommendationKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final CrudFurnitureTagRepository furnitureTagRepository;
    private final CrudRecommendationKeywordRepository recommendationKeywordRepository;
    private final CrudFurnitureRepository furnitureRepository;

    public RecommendationService(
            CrudFurnitureTagRepository furnitureTagRepository,
            CrudRecommendationKeywordRepository recommendationKeywordRepository,
            CrudFurnitureRepository furnitureRepository
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
