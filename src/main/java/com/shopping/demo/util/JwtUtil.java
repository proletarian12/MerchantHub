package com.shopping.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成Token（新增 role 参数）
     */
    public String generateToken(Long userId, String phone) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("phone", phone);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从Token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = parseToken(token);
            return Long.parseLong(claims.get("userId").toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从Token中获取手机号（新增）
     */
    public String getPhoneFromToken(String token) {
        try {
            Claims claims = parseToken(token);
            return (String) claims.get("phone");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从Token中获取角色（新增）
     */
    public String getRoleFromToken(String token) {
        try {
            Claims claims = parseToken(token);
            return (String) claims.get("role");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证Token是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断Token是否过期（新增）
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 刷新Token（新增，旧Token未过期时生成新Token）
     */
    public String refreshToken(String token) {
        Claims claims = parseToken(token);
        Long userId = Long.parseLong(claims.get("userId").toString());
        String phone = (String) claims.get("phone");
        String role = (String) claims.get("role");
        return generateToken(userId, phone);
    }

    // ==================== 私有方法 ====================

    /**
     * 解析Token，提取公共逻辑
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}