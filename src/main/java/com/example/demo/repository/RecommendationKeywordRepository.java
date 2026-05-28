package com.example.demo.repository;

import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.RecommendationKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationKeywordRepository extends JpaRepository<RecommendationKeyword, Long> {
    List<RecommendationKeyword> findByBaseTag(EmotionTag baseTag);
    List<RecommendationKeyword> findByBaseTag_Id(Long tagId);
}
