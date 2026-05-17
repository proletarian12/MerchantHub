// CartDao.java
package com.shopping.demo.dao;

import com.shopping.demo.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加商品到购物车
     */
    public int insert(CartItem cartItem) {
        String sql = "INSERT INTO cart_item (user_id, product_id, product_num, product_name, " +
                "product_price, product_image, store_name, quantity, added_time, selected) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                cartItem.getUserId(),
                cartItem.getProductId(),
                cartItem.getProductNum(),
                cartItem.getProductName(),
                cartItem.getProductPrice(),
                cartItem.getProductImage(),
                cartItem.getStoreName(),
                cartItem.getQuantity(),
                cartItem.getAddedTime(),
                cartItem.getSelected()
        );
    }

    /**
     * 根据用户ID和商品ID查询购物车项
     */
    public CartItem findByUserIdAndProductId(Long userId, String productId) {
        String sql = "SELECT * FROM cart_item WHERE user_id = ? AND product_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(CartItem.class), userId, productId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新购物车商品数量
     */
    public int updateQuantity(Long id, Integer quantity) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        return jdbcTemplate.update(sql, quantity, id);
    }

    /**
     * 获取用户的购物车列表
     */
    public List<CartItem> findByUserId(Long userId) {
        String sql = "SELECT * FROM cart_item WHERE user_id = ? ORDER BY added_time DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CartItem.class), userId);
    }

    /**
     * 删除购物车项
     */
    public int deleteById(Long id) {
        String sql = "DELETE FROM cart_item WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * 清空用户购物车
     */
    public int deleteByUserId(Long userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }

    /**
     * 更新选中状态
     */
    public int updateSelected(Long id, Boolean selected) {
        String sql = "UPDATE cart_item SET selected = ? WHERE id = ?";
        return jdbcTemplate.update(sql, selected, id);
    }

    /**
     * 批量更新选中状态
     */
    public int updateSelectedByUserId(Long userId, Boolean selected) {
        String sql = "UPDATE cart_item SET selected = ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, selected, userId);
    }
}