package com.example.OnlineAuctionApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private Integer amount;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private List<Product> product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;
    private LocalDateTime dateofcreated;
    @PrePersist
    public void init(){
        dateofcreated = LocalDateTime.now();
    }
    
}
