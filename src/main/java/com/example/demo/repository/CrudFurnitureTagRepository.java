package com.example.demo.repository;

import com.example.demo.entity.FurnitureTag;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface CrudFurnitureTagRepository extends JpaRepository<FurnitureTag, Long> {
    List<FurnitureTag> findByEmotionTag_Id(Long tagId);

    List<FurnitureTag> findByEmotionTag_IdIn(List<Long> tagIds);}
