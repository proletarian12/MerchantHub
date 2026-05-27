package com.shopping.demo.service;
import com.shopping.demo.dao.ProductDao;
import com.shopping.demo.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerProductService {

    @Autowired
    private ProductDao productDao;  // 注入的实例

    public List<ProductEntity> findBySeller(Long sellerId) {
        // 通过实例调用方法
        return productDao.findBySellerId(sellerId);
    }
}