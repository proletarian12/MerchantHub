package com.shopping.demo.entity;

import java.math.BigDecimal;

public class OrderItem {
    private Integer id; // 订单主键ID
    private Integer orderId; // 订单ID
    private Integer productId; // 商品ID
    private String productName; // 商品名称
    private String productImage; // 商品图片
    private BigDecimal price; // 商品单价
    private Integer quantity; // 购买数量

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}