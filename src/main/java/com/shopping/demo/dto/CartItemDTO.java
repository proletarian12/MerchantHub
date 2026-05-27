// CartItemDTO.java
package com.shopping.demo.dto;

import java.time.LocalDateTime;

public class CartItemDTO {
    private String productId;
    private String productNum;
    private String productName;
    private Double productPrice;
    private String productImage;
    private String storeName;
    private Integer quantity;
    private LocalDateTime addedTime;
    private Boolean selected;

    // Constructors
    public CartItemDTO() {}


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Double getTotalPrice() {
        if (productPrice != null && quantity != null) {
            return productPrice * quantity;
        }
        return 0.0;
    }


}

