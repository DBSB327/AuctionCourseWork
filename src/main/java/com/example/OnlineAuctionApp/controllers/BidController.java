package com.example.OnlineAuctionApp.controllers;

import com.example.OnlineAuctionApp.models.User;
import com.example.OnlineAuctionApp.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("/{productId}/{userId}/{amount}")
    public ResponseEntity<String> makeBid(@PathVariable Long productId, @PathVariable Long userId, @PathVariable Integer amount){
        try {
            bidService.makeBid(productId, userId, amount);
            return ResponseEntity.ok("Ставка успешно сделана");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/highestBid/{productId}")
    public ResponseEntity<Integer> getHighestBid(@PathVariable Long productId){
        return ResponseEntity.ok(bidService.getHighestBid(productId));
    }

    @GetMapping("/winningBidder/{productId}")
    public ResponseEntity<User> getWinningBidder(@PathVariable Long productId){
        User winningBidder = bidService.getWinningBidder(productId);
        if(winningBidder == null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(winningBidder);
        }
    }
}
