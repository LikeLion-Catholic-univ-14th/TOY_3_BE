package com.example.demo.service;
import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.RecommendationKeyword;
import com.example.demo.repository.EmotionTagRepository;
import com.example.demo.repository.RecommendationKeywordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiVisionService {
    @Value("${gemini.api-key}")
    private String apiKey;

    private final EmotionTagRepository emotionTagRepository;
    private final RecommendationKeywordRepository recommendationKeywordRepository;
    //private final GeminiService geminiService;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public Map<String, Object> analyzeImage(
            MultipartFile image
    ) throws IOException {

        // =====================
        // 이미지 base64 변환
        // =====================

        String base64Image = Base64.getEncoder()
                .encodeToString(image.getBytes());

        String prompt = """
가구 또는 방 사진을 분석해.

반드시 아래 JSON 형식으로만 응답해.
다른 설명, 마크다운, 코드블록 절대 금지.

{
  "description":"현재 공간 분석 결과",
  "tags":["태그1","태그2","태그3"]
}

규칙:
1. description은 2~3문장.
2. tags는 반드시 JSON 배열.
3. tags는 최소 3개 최대 5개.
4. 아래 목록 중에서만 선택.

[따뜻한, 포근한, 아늑한, 미니멀한, 깔끔한,
모던한, 감성적인, 차분한, 밝은, 화사한,
세련된, 빈티지, 러블리한, 심플한,
고급스러운, 편안한, 우드, 베이지,
브라운, 화이트, 내추럴, 원목 감성,
모노톤, 블랙, 파스텔, 소파,
테이블, 의자, 조명, 커튼]

응답 예시:

{
  "description":"현재 공간은 우드톤과 베이지 계열이 중심이라 따뜻하고 차분한 분위기입니다. 원목 가구가 잘 어울립니다.",
  "tags":["우드","베이지","따뜻한"]
}
""";
        log.info("[analyze] prompt={}", prompt);

        String requestBody = objectMapper.writeValueAsString(
                Map.of(
                        "contents", List.of(
                                Map.of(
                                        "parts", List.of(
                                                Map.of("text", prompt),
                                                Map.of(
                                                        "inline_data", Map.of(
                                                                "mime_type", image.getContentType(),
                                                                "data", base64Image
                                                        )
                                                )
                                        )
                                )
                        )
                )
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

        /*List<String> detectedTags =
                objectMapper.readValue(
                        text,
                        objectMapper.getTypeFactory()
                                .constructCollectionType(
                                        List.class,
                                        String.class
                                )
                );*/
        JsonNode analysis =
                objectMapper.readTree(text);

        String description =
                analysis.path("description")
                        .asText();

        if (description.isBlank()) {
            description = "분석 결과를 생성하지 못했습니다.";
        }

        JsonNode tagsNode = analysis.get("tags");

        if (tagsNode == null || !tagsNode.isArray()) {
            throw new RuntimeException("Gemini가 tags 배열을 반환하지 않았습니다.");
        }
        List<String> detectedTags =
                objectMapper.convertValue(
                        tagsNode,
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
/*
        String description =
                geminiService.generateDescription(
                        title,
                        finalTags
                );
*/
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
