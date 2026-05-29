package com.example.demo.service;

import com.example.demo.dto.GeminiRequest;
import com.example.demo.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {
    @Value("${gemini.api-key}")
    private String apikey;

    private final WebClient webClient =
            WebClient.builder()
                    .baseUrl("https://generativelanguage.googleapis.com")
                    .build();
    public String generateDescription(String title,
                                      List<String> tags) {

        String prompt = """
            중고 가구 판매글을 작성해줘.

            가구명 : %s
            감성태그 : %s

            100자 이내로 작성.
            """
                .formatted(title,
                        String.join(", ", tags));

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

        return response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();
    }
}
