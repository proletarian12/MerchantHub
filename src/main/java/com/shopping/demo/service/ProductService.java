package com.shopping.demo.service;

import com.shopping.demo.dto.ProductDTO;
import com.shopping.demo.entity.ProductEntity;
import com.shopping.demo.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 搜索产品
    public List<ProductDTO> searchProducts(String keyword) {
        List<ProductEntity> products = productRepository.findByProductNameContaining(keyword);
        return convertToDTOList(products);
    }



    // 获取所有产品
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return convertToDTOList(products);
    }

    // 转换方法
    private List<ProductDTO> convertToDTOList(List<ProductEntity> entities) {
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(ProductEntity p) {
        ProductDTO dto = new ProductDTO();
        dto.setProductNum(p.getProductNum());
        dto.setProductPrice(p.getProductPrice());
        dto.setProductName(p.getProductName());
        dto.setProductDescription(p.getProductDescription());
        dto.setStoreName(p.getStoreName());
        dto.setStoreAvatar(p.getStoreAvatar());
        dto.setWarranty(p.getWarranty());
        dto.setStockStatus(p.getStockStatus());
        dto.setService(p.getService());

        // 直接赋值，无需任何 JSON 转换！
        dto.setImages(p.getImages());
        dto.setDetailImages(p.getDetailImages());
        dto.setParameters(p.getParameters());
        dto.setShipping(p.getShippingInfo());
        dto.setReviews(new ArrayList<>());

        return dto;
    }
}