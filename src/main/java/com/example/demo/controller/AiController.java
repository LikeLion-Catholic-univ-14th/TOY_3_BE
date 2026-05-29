package com.example.demo.controller;


import com.example.demo.dto.GeminiRequest;

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

    private final GeminiVisionService geminiVisionService;


    @PostMapping(value ="/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> recommendTags(
            @RequestPart("image") MultipartFile image
    ) throws Exception {

        return geminiVisionService.analyzeImage(image);
    }
}
