package com.example.OnlineAuctionApp.repositories;


import com.example.OnlineAuctionApp.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByProductIdOrderByAmountDesc(Long productId);
}
