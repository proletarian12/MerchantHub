package com.shopping.demo.controller;

import com.shopping.demo.service.UserService;
import com.shopping.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        String nickname = params.get("nickname");

        // 简单校验
        if (phone == null || phone.length() != 11) {
            return Map.of("code", 400, "message", "手机号格式错误");
        }
        if (password == null || password.length() < 6) {
            return Map.of("code", 400, "message", "密码长度不能小于6位");
        }

        return userService.register(phone, password, nickname);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");

        if (phone == null || password == null) {
            return Map.of("code", 400, "message", "手机号和密码不能为空");
        }

        Map<String, Object> result = userService.login(phone, password);

        if ((int) result.get("code") != 200) {
            return result;
        }

        Long userId = (Long) result.get("userId");
        String userPhone = (String) result.get("phone");
        String role = (String) result.get("role");

        String token = jwtUtil.generateToken(userId, userPhone);

        result.put("token", token);
        return result;
    }

    /**
     * 获取用户信息（需要Token）
     */
    @GetMapping("/info")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        // 从Header获取Token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Map.of("code", 401, "message", "请先登录");
        }

        // 验证Token
        token = token.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            return Map.of("code", 401, "message", "登录已过期，请重新登录");
        }

        return userService.getUserInfo(userId);
    }

    /**
     * 测试接口
     */
    @GetMapping("/test")
    public String test() {
        return "商城系统运行正常";
    }
}