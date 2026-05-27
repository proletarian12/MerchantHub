package com.shopping.demo.controller;

import com.shopping.demo.dto.ProductDTO;
import com.shopping.demo.service.OssService;
import com.shopping.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OssService ossService;

    /**
     * 前端获取临时上传凭证
     */
    @GetMapping("/token")
    public Map<String, Object> getOssToken() {
        return ossService.generateOssToken();
    }

    // 搜索产品接口
    @GetMapping("/searchProduct")
    public Map<String, Object> searchProduct(@RequestParam String msg) {
        List<ProductDTO> products = productService.searchProducts(msg);
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        return response;
    }

    // 获取所有产品
    @GetMapping("/products")
    public Map<String, Object> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        return response;
    }

    // 上传产品
    @PostMapping("/product")
    public Map<String, Object> addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "产品添加成功");
        return response;
    }
}