package com.example.demo.controller;

import com.example.demo.dto.FurnitureCreateRequest;
import com.example.demo.dto.GeminiRequest;
import com.example.demo.service.GeminiService;
import com.example.demo.service.GeminiVisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {
    private final GeminiService geminiService;
    @GetMapping("/description")
    public String generateDescription(@RequestBody FurnitureCreateRequest request){
        return geminiService.generateDescription(
                request.getTitle(),
                request.getTagIds()
                        .stream()
                        .map(String::valueOf)
                        .toList()
        );
    }
    private final GeminiVisionService geminiVisionService;

    @PostMapping("/analyze")
    public Map<String, Object> recommendTags(
            @RequestParam MultipartFile image,
            @RequestParam String title
    ) throws Exception {

        return geminiVisionService.analyzeImage(image, title);
    }
}
