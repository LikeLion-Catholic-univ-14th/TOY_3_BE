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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiVisionService {
    @Value("${gemini.api-key}")
    private String apiKey;

    private final EmotionTagRepository emotionTagRepository;
    private final RecommendationKeywordRepository recommendationKeywordRepository;
    private final GeminiService geminiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> analyzeImage(
            MultipartFile image,
            String title
    ) throws IOException {
        log.info("[analyze] start title={}, imageName={}, contentType={}, size={}",
                title,
                image.getOriginalFilename(),
                image.getContentType(),
                image.getSize());

        String base64Image = java.util.Base64.getEncoder()
                .encodeToString(image.getBytes());
        log.info("[analyze] image encoded base64Length={}", base64Image.length());

        String prompt =
                "가구 사진을 분석해서 어울리는 분위기, 소재, 가구유형 태그를 3개에서 5개 골라줘. " +
                        "반드시 아래 태그 목록 중 의미가 가장 가까운 태그명만 그대로 사용해줘. " +
                        "빈 배열은 반환하지 마. 설명 문장 없이 JSON 배열만 반환해. " +
                        "태그 목록: [\"따뜻한\", \"포근한\", \"아늑한\", \"미니멀한\", \"깔끔한\", \"모던한\", " +
                        "\"감성적인\", \"차분한\", \"밝은\", \"화사한\", \"세련된\", \"빈티지\", \"러블리한\", " +
                        "\"심플한\", \"고급스러운\", \"편안한\", \"우드\", \"베이지\", \"브라운\", \"화이트\", " +
                        "\"내추럴\", \"원목 감성\", \"모노톤\", \"블랙\", \"파스텔\", \"소파\", \"테이블\", " +
                        "\"의자\", \"조명\", \"커튼\"]. " +
                        "예시: [\"우드\", \"브라운\", \"원목 감성\", \"따뜻한\"]";
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
        log.info("[analyze] gemini vision request ready mimeType={}, requestLength={}",
                image.getContentType(),
                requestBody.length());

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                        + apiKey;
        log.info("[analyze] calling gemini vision model=gemini-2.5-flash");

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        String.class
                );
        log.info("[analyze] gemini vision response status={}, bodyLength={}",
                response.getStatusCode(),
                response.getBody() == null ? 0 : response.getBody().length());

        JsonNode root = objectMapper.readTree(response.getBody());

        String text = root
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
        log.info("[analyze] raw gemini text={}", text);

        text = text
                .replace("```json", "")
                .replace("```", "")
                .trim();
        log.info("[analyze] cleaned gemini text={}", text);

        List<String> detectedTags =
                objectMapper.readValue(
                        text,
                        objectMapper.getTypeFactory()
                                .constructCollectionType(
                                        List.class,
                                        String.class
                                )
                );
        log.info("[analyze] detectedTags={}", detectedTags);

        List<String> finalTags = new ArrayList<>();

        for (String tagName : detectedTags) {
            log.info("[analyze] finding base tag name={}", tagName);

            EmotionTag baseTag =
                    emotionTagRepository
                            .findByName(tagName)
                            .orElse(null);

            if (baseTag == null) {
                log.warn("[analyze] base tag not found name={}", tagName);
                continue;
            }
            log.info("[analyze] base tag found id={}, name={}", baseTag.getId(), baseTag.getName());

            finalTags.add(baseTag.getName());

            List<RecommendationKeyword> recommendations =
                    recommendationKeywordRepository
                            .findByBaseTag_Id(baseTag.getId());
            log.info("[analyze] recommendations found baseTagId={}, count={}",
                    baseTag.getId(),
                    recommendations.size());

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
        log.info("[analyze] finalTags={}", finalTags);

        log.info("[analyze] calling description generator title={}, tagCount={}",
                title,
                finalTags.size());
        String description =
                geminiService.generateImageAnalysisDescription(
                        title,
                        finalTags
                );
        log.info("[analyze] description generated length={}",
                description == null ? 0 : description.length());

        Map<String, Object> result = new HashMap<>();

        result.put("detectedTags", detectedTags);
        result.put("recommendedTags", finalTags);
        result.put("description", description);
        log.info("[analyze] success detectedTagCount={}, recommendedTagCount={}",
                detectedTags.size(),
                finalTags.size());

        return result;
    }
}
