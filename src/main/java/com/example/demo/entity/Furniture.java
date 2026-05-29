package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Builder
public class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="furniture_id")
    private Long id;

    private String title;
    private Integer price;

    @Column(length = 1000)
    private String description;

    private String imageUrl;
    @Builder.Default
    @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FurnitureTag> furnitureTags = new ArrayList<>();


}
