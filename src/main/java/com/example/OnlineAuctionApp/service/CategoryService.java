package com.example.OnlineAuctionApp.service;

import com.example.OnlineAuctionApp.models.Category;
import com.example.OnlineAuctionApp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(Category category){
        categoryRepository.save(category);
    }
}
