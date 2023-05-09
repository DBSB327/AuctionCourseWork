package com.example.OnlineAuctionApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "category_title")
    private String categorytitle;
    @Column(name = "description")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
