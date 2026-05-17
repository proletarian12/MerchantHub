package com.shopping.demo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Orders {
    private Integer id; // 订单主键 ID
    private String orderNo; // 订单编号
    private Integer userId; // 用户 ID
    private BigDecimal totalAmount; // 订单总金额
    private String paymentMethod; // 支付方式\
    private Integer status; // 订单状态：0 - 待支付 1 - 已支付 2 - 已取消
    private Integer addressId; // 收货地址 ID
    private Date createTime; // 订单创建时间
    private Date payTime; // 支付时间

    private List<OrderItem> items; // 订单项列表

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getPayTime() { return payTime; }
    public void setPayTime(Date payTime) { this.payTime = payTime; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}