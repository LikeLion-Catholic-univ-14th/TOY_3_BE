package com.example.demo.controller;

import com.example.demo.dto.FurnitureCreateRequest;
import com.example.demo.dto.GeminiRequest;
import com.example.demo.service.GeminiService;
import com.example.demo.service.GeminiVisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {
    private final GeminiService geminiService;
    @PostMapping("/description")
    public String generateDescription(@RequestBody FurnitureCreateRequest request){
        return geminiService.generateDescription(
                request.getTitle(),
                request.getFinalTag()
                        .stream()
                        .map(String::valueOf)
                        .toList()
        );
    }
    private final GeminiVisionService geminiVisionService;


    @PostMapping(value ="/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> recommendTags(
            @RequestPart("image") MultipartFile image,
            @RequestParam("title") String title
    ) throws Exception {

        return geminiVisionService.analyzeImage(image, title);
    }
}
