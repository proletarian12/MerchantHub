package com.shopping.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUploadDTO {
    private String productName;        // 对应前端 productName
    private BigDecimal price;          // 对应前端 price
    private String description;        // 对应前端 description
    // 文件用 @RequestParam 单独接收，不放在 DTO 里
}