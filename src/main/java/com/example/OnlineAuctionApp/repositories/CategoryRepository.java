package com.example.OnlineAuctionApp.repositories;


import com.example.OnlineAuctionApp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
