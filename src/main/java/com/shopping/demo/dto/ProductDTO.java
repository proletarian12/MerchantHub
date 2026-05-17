package com.shopping.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProductDTO {
    private String productNum;
    private BigDecimal productPrice;
    private String productName;
    private String productDescription;
    private String brand;
    private String storeName;
    private String storeAvatar;
    private BigDecimal rating;
    private String warranty;
    private String stockStatus;
    private String service;
    private Map<String, String> parameters;
    private List<ImageDTO> images;
    private List<String> detailImages;
    private List<ReviewDTO> reviews;
    private ShippingDTO shipping;

    @Data
    public static class ImageDTO {
        private String thumb;
        private String full;
    }

    @Data
    public static class ReviewDTO {
        private Long userId;
        private String content;
        private BigDecimal rating;
        private String createdAt;
    }

    @Data
    public static class ShippingDTO {
        private String label;
        private String promise;
        private String method;
        private String location;
        private String returnPolicy;
        private String refund;
    }
}