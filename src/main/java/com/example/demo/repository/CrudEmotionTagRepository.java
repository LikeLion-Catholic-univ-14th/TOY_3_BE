package com.example.demo.repository;

import com.example.demo.entity.EmotionTag;
import com.example.demo.entity.FurnitureTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrudEmotionTagRepository extends JpaRepository<EmotionTag, Long> {

}

