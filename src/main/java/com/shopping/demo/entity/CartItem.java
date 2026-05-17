// CartItem.java
package com.shopping.demo.entity;

import java.time.LocalDateTime;

public class CartItem {
    private Long id; // 用户车每个商品的主键ID(顺序)
    private Long userId; // 用户ID
    private String productId; // Product里的商品ID
    private String productNum; // 商品编号
    private String productName; // 商品名称
    private Double productPrice; // 商品价格
    private String productImage; // 商品图片
    private String storeName; // 店铺名称
    private Integer quantity; // 商品数量
    private LocalDateTime addedTime; // 添加时间
    private Boolean selected; // 是否选中

    // Constructors
    public CartItem() {}

    public CartItem(Long userId, String productId, String productNum, String productName,
                    Double productPrice, String productImage, String storeName,
                    Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productNum = productNum;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.storeName = storeName;
        this.quantity = quantity;
        this.addedTime = LocalDateTime.now();
        this.selected = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
}