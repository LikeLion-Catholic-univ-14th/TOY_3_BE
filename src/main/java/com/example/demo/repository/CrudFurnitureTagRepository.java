package com.example.demo.repository;

import com.example.demo.entity.FurinitureTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrudFurnitureTagRepository extends JpaRepository<FurinitureTag, Long> {
    List<FurinitureTag> findByEmotionTag_Id(Long tagId);
}
