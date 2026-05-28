package com.example.demo.service;

import com.example.demo.dto.FurnitureCreateRequest;
import com.example.demo.dto.FurnitureResponse;
import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.Furniture;
import com.example.demo.entity.FurnitureTag;
import com.example.demo.repository.EmotionTagRepository;
import com.example.demo.repository.FurnitureRepository;
import com.example.demo.repository.FurnitureTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureService {
    private final FurnitureRepository furnitureRepository;
    private final EmotionTagRepository emotionTagRepository;
    private final FurnitureTagRepository furnitureTagRepository;
    private final GeminiService geminiService;

    @Transactional
    public FurnitureResponse createFurniture(FurnitureCreateRequest request){


        if(request.getTagIds().size()>5){
                throw new IllegalArgumentException("태그는 최대 5개까지 선택 가능합니다.");
        }

        List<EmotionTag> tags= emotionTagRepository.findAllById(request.getTagIds());
        List<String> tagNames=tags.stream().map(EmotionTag::getName).toList();
        String aiDescription;
        try {
            aiDescription =
                    geminiService.generateDescription(request.getTitle(), tagNames);
        }
        catch(Exception e){
            aiDescription=
                    String.join(",",tagNames)+" 분위기의 감성 가구 입니다.";
        }
        Furniture furniture=Furniture.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .description(aiDescription)
                .build();
        furnitureRepository.save(furniture);

        for(EmotionTag tag: tags){
            FurnitureTag furnitureTag=
                    FurnitureTag.builder()
                            .furniture(furniture)
                            .emotionTag(tag)
                            .build();
            furnitureTagRepository.save(furnitureTag);
            furniture.getFurnitureTags().add(furnitureTag);
        }
        List<String> tagNamesResponse =
                tags.stream()
                        .map(EmotionTag::getName)
                        .toList();

        return FurnitureResponse.builder()
                .id(furniture.getId())
                .title(furniture.getTitle())
                .price(furniture.getPrice())
                .description(furniture.getDescription())
                .imageUrl(furniture.getImageUrl())
                .tags(tagNamesResponse)
                .build();
    };


}
