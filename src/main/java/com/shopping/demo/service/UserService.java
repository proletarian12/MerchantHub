// UserService.java
package com.shopping.demo.service;

import com.shopping.demo.dao.UserDao;
import com.shopping.demo.entity.User;
import com.shopping.demo.util.JwtUtil;
import com.shopping.demo.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @Transactional
    public Map<String, Object> register(String phone, String password, String nickname) {
        Map<String, Object> result = new HashMap<>();

        // 1. 检查手机号是否已存在
        User existUser = userDao.findByPhone(phone);
        if (existUser != null) {
            result.put("code", 400);
            result.put("message", "手机号已注册");
            return result;
        }

        // 2. 创建新用户
        User user = new User();
        user.setPhone(phone);
        user.setPassword(PasswordUtil.hash(password));
        user.setNickname(nickname != null ? nickname : "用户" + phone.substring(7));
        user.setLastLoginTime(new Date());

        // 3. 保存到数据库
        int rows = userDao.insert(user);
        if (rows > 0) {
            // 4. 生成Token
            String token = jwtUtil.generateToken(user.getId(), user.getPhone());

            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("phone", user.getPhone());
            data.put("nickname", user.getNickname());
            data.put("token", token);

            result.put("code", 200);
            result.put("message", "注册成功");
            result.put("data", data);
        } else {
            result.put("code", 500);
            result.put("message", "注册失败，请重试");
        }

        return result;
    }

    /**
     * 用户登录
     */
    public Map<String, Object> login(String phone, String password) {
        Map<String, Object> result = new HashMap<>();

        // 1. 查找用户
        User user = userDao.findByPhone(phone);
        if (user == null) {
            result.put("code", 400);
            result.put("message", "用户不存在");
            return result;
        }

        // 2. 验证密码
        if (!PasswordUtil.matches(password, user.getPassword())) {
            result.put("code", 400);
            result.put("message", "密码错误");
            return result;
        }

        // 3. 更新登录时间
        userDao.updateLastLoginTime(user.getId());

        // 4. 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getPhone());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("phone", user.getPhone());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("token", token);

        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("data", data);

        return result;
    }

    /**
     * 获取用户信息
     */
    public Map<String, Object> getUserInfo(Long userId) {
        Map<String, Object> result = new HashMap<>();

        User user = userDao.findById(userId);
        if (user == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("phone", user.getPhone());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());

        result.put("code", 200);
        result.put("data", data);

        return result;
    }
}