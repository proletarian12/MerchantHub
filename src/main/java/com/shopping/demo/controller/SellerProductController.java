package com.shopping.demo.controller;

import com.shopping.demo.entity.ProductEntity;
import com.shopping.demo.service.SellerProductService;
import com.shopping.demo.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerProductController {

    @Autowired
    private SellerProductService productService;

    @GetMapping("/products")
    public Result<ProductEntity> getProducts(Long id) {  // 不加泛型
        Long sellerId = id;
        List<ProductEntity> products = productService.findBySeller(sellerId);
        return Result.success(products);  // success 方法会自动封装
    }
}