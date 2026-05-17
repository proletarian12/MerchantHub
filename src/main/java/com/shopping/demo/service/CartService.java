// CartService.java - 完整版
package com.shopping.demo.service;

import com.shopping.demo.dao.ApiResponse;
import com.shopping.demo.dao.CartDao;
import com.shopping.demo.dto.CartItemDTO;
import com.shopping.demo.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    /**
     * 添加商品到购物车
     */
    @Transactional
    public ApiResponse<?> addToCart(CartItem cartItem) {
        // 检查商品是否已在购物车中
        CartItem existingItem = cartDao.findByUserIdAndProductId(
                cartItem.getUserId(), cartItem.getProductId());

        if (existingItem != null) {
            // 如果已存在，增加数量
            int newQuantity = existingItem.getQuantity() + cartItem.getQuantity();
            cartDao.updateQuantity(existingItem.getId(), newQuantity);
            return ApiResponse.success("购物车商品数量已更新", null);
        } else {
            // 如果不存在，新增
            cartDao.insert(cartItem);
            return ApiResponse.success("商品已添加到购物车", null);
        }
    }

    /**
     * 获取用户购物车列表
     */
    public ApiResponse<?> getCartList(Long userId) {
        List<CartItem> cartItems = cartDao.findByUserId(userId);

        // 转换为DTO并计算总价
        List<CartItemDTO> cartDTOList = cartItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 计算购物车汇总信息
        double totalAmount = cartDTOList.stream()
                .filter(CartItemDTO::getSelected)
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();

        int totalQuantity = cartDTOList.stream()
                .filter(CartItemDTO::getSelected)
                .mapToInt(CartItemDTO::getQuantity)
                .sum();

        // 构建返回数据
        CartSummary summary = new CartSummary();
        summary.setItems(cartDTOList);
        summary.setTotalAmount(totalAmount);
        summary.setTotalQuantity(totalQuantity);
        summary.setItemCount(cartDTOList.size());

        return ApiResponse.success(summary);
    }

    /**
     * 更新购物车商品数量
     */
    @Transactional
    public ApiResponse<?> updateQuantity(Long id, Integer quantity) {
        if (quantity <= 0) {
            // 如果数量小于等于0，删除该商品
            cartDao.deleteById(id);
            return ApiResponse.success("商品已从购物车移除", null);
        } else {
            cartDao.updateQuantity(id, quantity);
            return ApiResponse.success("数量更新成功", null);
        }
    }

    /**
     * 删除购物车商品
     */
    @Transactional
    public ApiResponse<?> deleteCartItem(Long id) {
        cartDao.deleteById(id);
        return ApiResponse.success("商品已从购物车移除", null);
    }

    /**
     * 批量删除购物车商品
     */
    @Transactional
    public ApiResponse<?> deleteCartItems(List<Long> ids) {
        for (Long id : ids) {
            cartDao.deleteById(id);
        }
        return ApiResponse.success("已移除选中的商品", null);
    }

    /**
     * 更新商品选中状态
     */
    @Transactional
    public ApiResponse<?> updateSelected(Long id, Boolean selected) {
        cartDao.updateSelected(id, selected);
        return ApiResponse.success("更新成功", null);
    }

    /**
     * 全选/取消全选
     */
    @Transactional
    public ApiResponse<?> selectAll(Long userId, Boolean selected) {
        cartDao.updateSelectedByUserId(userId, selected);
        String message = selected ? "已全选" : "已取消全选";
        return ApiResponse.success(message, null);
    }

    /**
     * 清空购物车
     */
    @Transactional
    public ApiResponse<?> clearCart(Long userId) {
        cartDao.deleteByUserId(userId);
        return ApiResponse.success("购物车已清空", null);
    }

    /**
     * 获取选中的商品（用于结算）
     */
    public ApiResponse<?> getSelectedItems(Long userId) {
        List<CartItem> cartItems = cartDao.findByUserId(userId);

        List<CartItemDTO> selectedItems = cartItems.stream()
                .filter(CartItem::getSelected)
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        double totalAmount = selectedItems.stream()
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();

        SelectedItemsResult result = new SelectedItemsResult();
        result.setItems(selectedItems);
        result.setTotalAmount(totalAmount);
        result.setItemCount(selectedItems.size());

        return ApiResponse.success(result);
    }

    /**
     * 实体转DTO
     */
    private CartItemDTO convertToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setUserId(item.getUserId());
        dto.setProductId(item.getProductId());
        dto.setProductNum(item.getProductNum());
        dto.setProductName(item.getProductName());
        dto.setProductPrice(item.getProductPrice());
        dto.setProductImage(item.getProductImage());
        dto.setStoreName(item.getStoreName());
        dto.setQuantity(item.getQuantity());
        dto.setAddedTime(item.getAddedTime());
        dto.setSelected(item.getSelected());
        return dto;
    }

    /**
     * 购物车汇总内部类
     */
    public static class CartSummary {
        private List<CartItemDTO> items;
        private Double totalAmount;
        private Integer totalQuantity;
        private Integer itemCount;

        // Getters and Setters
        public List<CartItemDTO> getItems() { return items; }
        public void setItems(List<CartItemDTO> items) { this.items = items; }

        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

        public Integer getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }

        public Integer getItemCount() { return itemCount; }
        public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }
    }

    /**
     * 选中商品结果内部类
     */
    public static class SelectedItemsResult {
        private List<CartItemDTO> items;
        private Double totalAmount;
        private Integer itemCount;

        // Getters and Setters
        public List<CartItemDTO> getItems() { return items; }
        public void setItems(List<CartItemDTO> items) { this.items = items; }

        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

        public Integer getItemCount() { return itemCount; }
        public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }
    }
}