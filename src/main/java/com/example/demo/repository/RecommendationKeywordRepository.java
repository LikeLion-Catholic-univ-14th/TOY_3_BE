package com.example.demo.repository;

import com.example.demo.entity.RecommendationKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationKeywordRepository extends JpaRepository<RecommendationKeyword, Long> {
    List<RecommendationKeyword> findByEmotionTag_Id(Long tagId);
}
