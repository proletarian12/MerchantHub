package com.shopping.demo.util;

import com.shopping.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // 白名单：不需要登录就能访问的路径
    private static final List<String> WHITE_LIST = List.of(
            "/login",
            "/register",
            "/goods/hot",
            "/goods/list"
    );

    // 角色-路径映射：key=角色名, value=该角色允许访问的路径前缀
    private static final Map<String, List<String>> ROLE_PATH_MAP = Map.of(
            "MERCHANT", List.of("/seller/"),
            "USER",     List.of("/user/"),
            "ADMIN",    List.of("/admin/")
    );


    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 1. 白名单直接放行
        if (isWhiteListed(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 获取 token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            sendError(response, 401, "未登录，请先登录");
            return;
        }

        try {
            token = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);

            // 3. 检查角色是否有权限访问这个路径
            if (!hasPermission(role, path)) {
                sendError(response, 403, "权限不足，拒绝访问");
                return;
            }

            // 4. 把 userId 存到 request 属性里，Controller 直接从 request 拿
            request.setAttribute("userId", userId);

        } catch (Exception e) {
            sendError(response, 401, "token 无效或已过期");
            return;
        }

        // 5. 全部通过，放行
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteListed(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }

    private boolean hasPermission(String role, String path) {
        List<String> allowedPaths = ROLE_PATH_MAP.get(role);
        if (allowedPaths == null) return false;
        return allowedPaths.stream().anyMatch(path::startsWith);
    }

    private void sendError(HttpServletResponse response, int code, String msg) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":null}");
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

    }
}