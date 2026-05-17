// CartController.java - 简化版
package com.shopping.demo.controller;

import com.shopping.demo.dao.ApiResponse;
import com.shopping.demo.entity.CartItem;
import com.shopping.demo.service.CartService;
import com.shopping.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shoppingcart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.getUserIdFromToken(token);
        }
        return null;
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping
    public ApiResponse<?> addToCart(
            HttpServletRequest request,
            @RequestBody CartItem cartItem) {

        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        cartItem.setUserId(userId);

        // 直接返回Service的ApiResponse
        return cartService.addToCart(cartItem);
    }

    /**
     * 获取购物车列表
     */
    @GetMapping
    public ApiResponse<?> getCartList(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        return cartService.getCartList(userId);
    }

    /**
     * 更新购物车商品数量
     */
    @PutMapping("/{id}")
    public ApiResponse<?> updateQuantity(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody Map<String, Integer> params) {

        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        Integer quantity = params.get("quantity");
        if (quantity == null) {
            return ApiResponse.error(400, "数量不能为空");
        }

        return cartService.updateQuantity(id, quantity);
    }

    /**
     * 删除购物车商品
     */
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteCartItem(
            HttpServletRequest request,
            @PathVariable Long id) {

        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        return cartService.deleteCartItem(id);
    }

    /**
     * 批量删除购物车商品
     */
    @DeleteMapping("/batch")
    public ApiResponse<?> deleteCartItems(
            HttpServletRequest request,
            @RequestBody Map<String, List<Long>> params) {

        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        List<Long> ids = params.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ApiResponse.error(400, "请选择要删除的商品");
        }

        return cartService.deleteCartItems(ids);
    }

    /**
     * 更新商品选中状态
     */
    @PutMapping("/{id}/selected")
    public ApiResponse<?> updateSelected(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> params) {

        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        Boolean selected = params.get("selected");
        if (selected == null) {
            return ApiResponse.error(400, "选中状态不能为空");
        }

        return cartService.updateSelected(id, selected);
    }

    /**
     * 全选/取消全选
     */
    @PutMapping("/select-all")
    public ApiResponse<?> selectAll(
            HttpServletRequest request,
            @RequestBody Map<String, Boolean> params) {

        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        Boolean selected = params.get("selected");
        if (selected == null) {
            return ApiResponse.error(400, "选中状态不能为空");
        }

        return cartService.selectAll(userId, selected);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    public ApiResponse<?> clearCart(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        return cartService.clearCart(userId);
    }

    /**
     * 获取选中的商品（用于结算）
     */
    @GetMapping("/selected")
    public ApiResponse<?> getSelectedItems(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        if (userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        return cartService.getSelectedItems(userId);
    }
}