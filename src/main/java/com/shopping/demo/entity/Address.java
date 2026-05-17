package com.shopping.demo.entity;

import java.util.Date;

public class Address {
    private Integer id; // 地址主键ID
    private Integer userId; // 用户ID
    private String name; // 收货人姓名
    private String phone; // 收货人电话
    private String province; // 省份
    private String city; // 城市
    private String district; // 区县
    private String detail; // 详细地址
    private Boolean isDefault; // 是否为默认地址



    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public Boolean getIsDefault() { return isDefault; }
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }


}