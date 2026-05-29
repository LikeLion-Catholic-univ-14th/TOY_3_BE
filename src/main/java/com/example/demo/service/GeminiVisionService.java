package com.example.demo.service;
import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.repository.EmotionTagRepository;
import com.example.demo.repository.RecommendationKeywordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GeminiVisionService {
    @Value("${gemini.api-key}")
    private String apiKey;

    private final EmotionTagRepository emotionTagRepository;
    private final RecommendationKeywordRepository recommendationKeywordRepository;
    private final GeminiService geminiService;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public Map<String, Object> analyzeImage(
            MultipartFile image,
            String title
    ) throws IOException {

        // =====================
        // 이미지 base64 변환
        // =====================

        String base64Image = Base64.getEncoder()
                .encodeToString(image.getBytes());

        // =====================
        // Vision AI 요청
        // =====================

        String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "가구 사진을 분석해서 분위기 태그만 JSON 배열로 반환해. 예시: [\\"우드\\", \\"따뜻한\\"]"
                        },
                        {
                          "inline_data": {
                            "mime_type": "%s",
                            "data": "%s"
                          }
                        }
                      ]
                    }
                  ]
                }
                """.formatted(
                image.getContentType(),
                base64Image
        );

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity =
                new HttpEntity<>(requestBody, headers);

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                        + apiKey;

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        // =====================
        // 응답 파싱
        // =====================

        JsonNode root =
                objectMapper.readTree(response.getBody());

        String text = root
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        text = text
                .replace("```json", "")
                .replace("```", "")
                .trim();

        List<String> detectedTags =
                objectMapper.readValue(
                        text,
                        objectMapper.getTypeFactory()
                                .constructCollectionType(
                                        List.class,
                                        String.class
                                )
                );

        // =====================
        // 추천 태그 조회
        // =====================

        List<String> finalTags = new ArrayList<>();

        for (String tagName : detectedTags) {

            EmotionTag baseTag =
                    emotionTagRepository
                            .findByName(tagName)
                            .orElse(null);

            if (baseTag == null) continue;

            finalTags.add(baseTag.getName());

            List<RecommendationKeyword> recommendations =
                    recommendationKeywordRepository
                            .findByBaseTag_Id(baseTag.getId());

            for (RecommendationKeyword recommendation : recommendations) {

                finalTags.add(
                        recommendation
                                .getRecommendedTag()
                                .getName()
                );
            }
        }

        finalTags = finalTags.stream()
                .distinct()
                .toList();

        // =====================
        // 설명 생성 서비스 호출
        // =====================

        String description =
                geminiService.generateDescription(
                        title,
                        finalTags
                );

        // =====================
        // 결과 반환
        // =====================

        Map<String, Object> result =
                new HashMap<>();

        result.put("detectedTags", detectedTags);
        result.put("recommendedTags", finalTags);
        result.put("description", description);

        return result;
    }
}
