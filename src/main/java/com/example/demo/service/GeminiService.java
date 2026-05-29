package com.example.demo.service;

import com.example.demo.dto.GeminiRequest;
import com.example.demo.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {
    @Value("${gemini.api-key}")
    private String apikey;

    private final WebClient webClient =
            WebClient.builder()
                    .baseUrl("https://generativelanguage.googleapis.com")
                    .build();

    public String generateDescription(String title, List<String> tags) {
        log.info("[description] start title={}, tags={}", title, tags);

        String prompt =
                "중고 가구 판매글을 작성해줘.\n" +
                        "가구명: " + title + "\n" +
                        "감성태그: " + String.join(", ", tags) + "\n" +
                        "100자 이내로 자연스럽게 작성해줘.";
        log.info("[description] prompt={}", prompt);
        log.info("[description] promptLength={}, tagCount={}", prompt.length(), tags.size());

        GeminiRequest request =
                new GeminiRequest(
                        List.of(
                                new GeminiRequest.Content(
                                        List.of(
                                                new GeminiRequest.Part(prompt)
                                        )
                                )
                        )
                );

        GeminiResponse response =
                webClient.post()
                        .uri("/v1beta/models/gemini-2.5-flash:generateContent?key="
                                + apikey)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GeminiResponse.class)
                        .block();
        log.info("[description] gemini response received candidates={}",
                response == null || response.getCandidates() == null ? 0 : response.getCandidates().size());

        String text = response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();
        log.info("[description] success textLength={}", text == null ? 0 : text.length());

        return text;
    }

    public String generateImageAnalysisDescription(String title, List<String> recommendedTags) {
        log.info("[analyze-description] start title={}, recommendedTags={}", title, recommendedTags);

        String prompt =
                "이미지 분석으로 추천된 태그를 바탕으로 중고 가구 판매 설명을 작성해줘.\n" +
                        "가구명: " + title + "\n" +
                        "추천태그: " + String.join(", ", recommendedTags) + "\n" +
                        "태그를 그대로 나열하지 말고 자연스러운 문장으로 작성해줘.\n" +
                        "100자 이내로 작성해줘.";
        log.info("[analyze-description] prompt={}", prompt);
        log.info("[analyze-description] promptLength={}, tagCount={}", prompt.length(), recommendedTags.size());

        GeminiRequest request =
                new GeminiRequest(
                        List.of(
                                new GeminiRequest.Content(
                                        List.of(
                                                new GeminiRequest.Part(prompt)
                                        )
                                )
                        )
                );

        GeminiResponse response =
                webClient.post()
                        .uri("/v1beta/models/gemini-2.5-flash:generateContent?key="
                                + apikey)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GeminiResponse.class)
                        .block();
        log.info("[analyze-description] gemini response received candidates={}",
                response == null || response.getCandidates() == null ? 0 : response.getCandidates().size());

        String text = response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();
        log.info("[analyze-description] success textLength={}", text == null ? 0 : text.length());

        return text;
    }
}
