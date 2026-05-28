package com.example.demo.controller;

import com.example.demo.dto.FurnitureCreateRequest;
import com.example.demo.dto.FurnitureResponse;
import com.example.demo.entity.Furniture;
import com.example.demo.service.FurnitureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/furniture")
public class FurnitureController {
    private final FurnitureService furnitureService;

    @PostMapping
    public FurnitureResponse creat(@RequestBody FurnitureCreateRequest request){
        return furnitureService.createFurniture(request);
    }
}
