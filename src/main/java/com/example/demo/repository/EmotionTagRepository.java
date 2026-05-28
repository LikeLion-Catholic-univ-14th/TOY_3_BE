package com.example.demo.repository;

import com.example.demo.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {
}
