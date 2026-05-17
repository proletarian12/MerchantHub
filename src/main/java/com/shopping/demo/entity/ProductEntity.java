package com.shopping.demo.entity;

import com.shopping.demo.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productNum;        // 商品编号 / 商品编码
    private String productName;      // 商品名称
    private BigDecimal productPrice; // 商品价格
    private String productDescription; // 商品描述
    private String storeName;        // 店铺名称
    private String storeAvatar;      // 店铺头像
    private String warranty;         // 保修服务 / 质保
    private String stockStatus;      // 库存状态（有货/缺货）
    private String service;          // 服务（售后、配送服务等）

    // 图片列表 - 自动映射为 JSON
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT")
    private List<ProductDTO.ImageDTO> images;

    // 详情图片列表
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT")
    private List<String> detailImages;

    // 参数键值对
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT")
    private Map<String, String> parameters;

    // 配送信息
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT")
    private ProductDTO.ShippingDTO shippingInfo;
}