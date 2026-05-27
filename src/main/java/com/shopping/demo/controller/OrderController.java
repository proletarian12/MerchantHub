package com.shopping.demo.controller;

import com.shopping.demo.entity.Orders;
import com.shopping.demo.entity.OrderItem;
import com.shopping.demo.entity.Result;
import com.shopping.demo.service.OrderService;
import com.shopping.demo.util.JwtUtil;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 创建订单
     */
    @PostMapping("/create-order")
    public Result<Order> createOrder(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            // 从token中获取用户ID
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error("未登录或token已过期");
            }

            // 解析订单参数
            Integer productId = (Integer) params.get("productId");
            Integer quantity = (Integer) params.get("quantity");
            BigDecimal productPrice = new BigDecimal(params.get("productPrice").toString());
            String paymentMethod = (String) params.get("paymentMethod");

            // 获取地址信息
            Map<String, Object> addressMap = (Map<String, Object>) params.get("address");
            Integer addressId = addressMap != null ? (Integer) addressMap.get("id") : null;

            // 创建订单
            Orders order = new Orders();
            order.setUserId(userId);
            order.setOrderNo(generateOrderNo());
            order.setTotalAmount(productPrice.multiply(new BigDecimal(quantity)));
            order.setPaymentMethod(paymentMethod);
            order.setStatus(0); // 待支付
            order.setAddressId(addressId);

            // 创建订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(productId);
            orderItem.setProductName((String) params.get("productName"));
            orderItem.setProductImage((String) params.get("productImage"));
            orderItem.setPrice(productPrice);
            orderItem.setQuantity(quantity);

            // 保存订单
            int orderId = orderService.createOrder(order, orderItem);

            // 模拟支付参数返回
            Map<String, Object> payData = new HashMap<>();
            payData.put("orderId", orderId);
            payData.put("orderNo", order.getOrderNo());

            // 根据支付方式返回不同的支付参数
            if ("wechat".equals(paymentMethod)) {
                // 模拟微信支付返回
                payData.put("payType", "qr_code");
                payData.put("codeUrl", "weixin://wxpay/bizpayurl?pr=" + UUID.randomUUID().toString().substring(0, 8));
                payData.put("payUrl", "https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=" + UUID.randomUUID().toString());
            } else if ("alipay".equals(paymentMethod)) {
                // 模拟支付宝支付返回
                payData.put("payUrl", "https://openapi.alipay.com/gateway.do?trade_no=" + UUID.randomUUID().toString());
            }

            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("data", payData);
            result.put("message", "订单创建成功");

            return Result.success(payData, "订单创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订单创建失败：" + e.getMessage());
        }
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/order/{orderId}")
    public Result<Order> getOrderDetail(@PathVariable Integer orderId, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error(401, "未登录或token已过期");
            }

            Orders order = orderService.findById(orderId, userId);
            if (order == null) {
                return Result.error("订单不存在");
            }

            return Result.success(order);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取订单失败：" + e.getMessage());
        }
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4);
    }

    /**
     * 从token中获取用户ID
     */
    private Integer getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long userId = jwtUtil.getUserIdFromToken(token);
            return userId.intValue();
        }
        return null;
    }
}