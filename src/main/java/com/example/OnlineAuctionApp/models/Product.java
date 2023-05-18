    package com.example.OnlineAuctionApp.models;

    import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;
        @Column(name = "name")
        private String name;
        @Column(name = "start_price")
        private Integer startprice;
        @Column(name = "description")
        private String description;
        @Column(name = "image")
        private String image;
        @Column(name = "isActive")
        private Boolean isActive;
        @Column(name = "end_time")
        private LocalDateTime endTime;
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="seller_id")
        private User seller;
        @JsonIgnore
        @ManyToMany
        private List<Bid> bids;
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        @JoinColumn(name="category_id")
        private Category category;

    }
