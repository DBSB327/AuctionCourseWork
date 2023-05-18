package com.example.OnlineAuctionApp.repositories;

import com.example.OnlineAuctionApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductsByName(String name);

}
