package com.example.OnlineAuctionApp.controllers;

import com.example.OnlineAuctionApp.models.Product;
import com.example.OnlineAuctionApp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/search")
    public ResponseEntity<Product> getProductById(@RequestBody Product product){
        return ResponseEntity.ok(productService.getProductsByName(product));
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProductById(@PathVariable Long id){
        if(!productService.getProductById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping ("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String uploadImage = productService.uploadImage(multipartFile);
        return ResponseEntity.ok(uploadImage);
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] downloadImage = productService.downloadImage(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage);
    }

}
