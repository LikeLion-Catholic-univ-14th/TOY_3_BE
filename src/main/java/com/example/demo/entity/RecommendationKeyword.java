package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendationKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommendation_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="base_tag_id")
    private EmotionTag baseTag;
    @ManyToOne
    @JoinColumn(name = "recommended_tag_id")
    private EmotionTag recommendedTag;

    private String keyword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id")
    private EmotionTag emotionTag;

}
