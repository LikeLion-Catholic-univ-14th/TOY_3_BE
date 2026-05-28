package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FurnitureResponse {
    private Long id;
    private String title;
    private Integer price;
    private String description;
    private String imageUrl;

    private List<String> tags;
}
