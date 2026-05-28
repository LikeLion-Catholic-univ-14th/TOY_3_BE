package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmotionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "emotionTag")
    private List<FurnitureTag> furnitureTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TagCategory category;

    @OneToMany(mappedBy = "baseTag")
    private List<RecommendationKeyword> baseRecommendations
            = new ArrayList<>();



}
