package com.example.demo.repository;

import com.example.demo.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudFurnitureRepository extends JpaRepository<Furniture, Long> {
}