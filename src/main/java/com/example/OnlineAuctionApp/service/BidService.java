package com.example.OnlineAuctionApp.service;

import com.example.OnlineAuctionApp.models.Bid;
import com.example.OnlineAuctionApp.models.Product;
import com.example.OnlineAuctionApp.models.User;
import com.example.OnlineAuctionApp.repositories.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final ProductService productService;
    private final UserService userService;

    public void makeBid(Long productId, Long userId, Integer amount) throws Exception {
        Optional<Product> product = productService.getProductById(productId);
        if(product.isEmpty()){
            throw new Exception("Товар не найден");
        }
        Optional<User> user = userService.getUserById(userId);
        if(user.isEmpty()){
            throw new Exception("Пользователь не найден");
        }

        if (!product.get().getIsActive()) {
            throw new Exception("Торги по этому товару закрыты.");
        }

        Integer highestBid = getHighestBid(productId);
        if (amount <= highestBid) {
            throw new Exception("Сумма ставки должна быть выше, чем текущая самая высокая ставка.");
        }


        Bid bid = new Bid();
        bid.setAmount(amount);
        bid.init();
        bid.setProduct(Collections.singletonList(product.get()));
        bid.setBuyer(user.get());
        List<Bid> bids = product.get().getBids();
        if(bids==null){
            bids = new ArrayList<>();
            product.get().setBids(bids);
        }

        bids.add(bid);

        bidRepository.save(bid);

        product.get().setStartprice(highestBid);
        productService.updateProduct(product.get());
    }

    public Integer getHighestBid(Long productId) {
        List<Bid> bids = bidRepository.findByProductIdOrderByAmountDesc(productId);
        if (bids.isEmpty()) {
            return 0;
        } else {
            return bids.get(0).getAmount();
        }
    }

    public User getWinningBidder(Long productId) {
        Optional<Product> product = productService.getProductById(productId);
        List<Bid> bids = bidRepository.findByProductIdOrderByAmountDesc(productId);

        if (bids.isEmpty()) {
            return null;
        }

        Bid highestBid = bids.get(0);

        LocalDateTime endTime = product.get().getEndTime();
        if (highestBid.getDateofcreated().isBefore(endTime)) {
            return null;
        }

        return highestBid.getBuyer();
    }
}
