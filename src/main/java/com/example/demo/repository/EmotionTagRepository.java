package com.example.demo.repository;

import com.example.demo.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {
    Optional<EmotionTag> findByName(String name);
}
