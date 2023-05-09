package com.example.OnlineAuctionApp.service;

import com.example.OnlineAuctionApp.models.Product;
import com.example.OnlineAuctionApp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductsByName(Product product) {
       return productRepository.getProductsByName(product.getName());
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        LocalDateTime endTime = product.getEndTime();
        LocalDateTime currentTime = LocalDateTime.now();

        if(endTime.isBefore(currentTime)){
            throw new IllegalArgumentException("End time cant be before current time, dude");
        }
        long secondsUntilExpiration = Duration.between(currentTime, endTime).getSeconds();
        TimerTask deletetask = new TimerTask() {
            @Override
            public void run() {
                productRepository.delete(product);
            }
        };
        Timer timer = new Timer();
        timer.schedule(deletetask, secondsUntilExpiration * 1000);
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product updatedProduct) {
        Optional<Product> optionalProduct = getProductById(updatedProduct.getId());
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setStartprice(updatedProduct.getStartprice());
            existingProduct.setBids(updatedProduct.getBids());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setSeller(updatedProduct.getSeller());
            existingProduct.setEndTime(updatedProduct.getEndTime());
            return productRepository.save(existingProduct);
        }
        return null;
    }

}

