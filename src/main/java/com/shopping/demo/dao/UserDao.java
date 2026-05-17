package com.shopping.demo.dao;

import com.shopping.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据手机号查询用户
     */
    public User findByPhone(String phone) {
        String sql = "SELECT * FROM user WHERE phone = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class), phone);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据ID查询用户
     */
    public User findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 插入用户
     */
    public int insert(User user) {
        String sql = "INSERT INTO user (phone, password, nickname, avatar, last_login_time) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getPhone(),
                user.getPassword(),
                user.getNickname(),
                user.getAvatar(),
                user.getLastLoginTime());
    }

    /**
     * 更新最后登录时间
     */
    public int updateLastLoginTime(Long id) {
        String sql = "UPDATE user SET last_login_time = NOW() WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}