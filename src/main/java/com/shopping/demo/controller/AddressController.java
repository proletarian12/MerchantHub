package com.shopping.demo.controller;

import com.shopping.demo.entity.Address;
import com.shopping.demo.entity.Result;

import com.shopping.demo.service.AddressService;
import com.shopping.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class AddressController {


    @Autowired
    private AddressService addressService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取用户地址列表
     */
    @GetMapping("/addresses")
    public Result getAddresses(HttpServletRequest request) {
        try {
            // 从token中获取用户ID
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error(401, "未登录或token已过期");
            }

            List<Address> addresses = addressService.findByUserId(userId);
            return Result.success(addresses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取地址列表失败：" + e.getMessage());
        }
    }

    /**
     * 添加新地址
     */
    @PostMapping("/address")
    public Result addAddress(@RequestBody Address address, HttpServletRequest request) {
        try {
            // 从token中获取用户ID
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error(401, "未登录或token已过期");
            }

            address.setUserId(userId);

            // 如果设置为默认地址，需要先取消其他默认地址
            if (address.getIsDefault()) {
                addressService.clearDefaultAddress(userId);
            }

            int id = addressService.save(address);
            address.setId(id);

            return Result.success(address, "地址添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("地址添加失败：" + e.getMessage());
        }
    }

    /**
     * 更新地址
     */
    @PutMapping("/address/{id}")
    public Result updateAddress(@PathVariable Integer id, @RequestBody Address address, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error(401, "未登录或token已过期");
            }

            address.setId(id);
            address.setUserId(userId);

            // 如果设置为默认地址，需要先取消其他默认地址
            if (address.getIsDefault()) {
                addressService.clearDefaultAddress(userId);
            }

            addressService.update(address);
            return Result.success(null, "地址更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("地址更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除地址
     */
    @DeleteMapping("/address/{id}")
    public Result deleteAddress(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error(401, "未登录或token已过期");
            }

            addressService.delete(id, userId);
            return Result.success(null, "地址删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("地址删除失败：" + e.getMessage());
        }
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/address/{id}/default")
    public Result setDefaultAddress(@PathVariable Integer id, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            if (userId == null) {
                return Result.error(401, "未登录或token已过期");
            }

            // 先清除所有默认地址
            addressService.clearDefaultAddress(userId);
            // 设置新的默认地址
            addressService.setDefaultAddress(id, userId);

            return Result.success(null, "默认地址设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("设置默认地址失败：" + e.getMessage());
        }
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