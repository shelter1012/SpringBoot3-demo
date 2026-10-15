package com.example.demo.intercepter;

import com.example.demo.common.UserContext;
import com.example.demo.common.utils.JwtUtils;
import com.example.demo.entity.SysUser;
import com.example.demo.service.SysUserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头拿 token
        String authHeader = request.getHeader("Authorization");
        System.out.println("【拦截器】authHeader = " + authHeader);   // ← 加这行

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("【拦截器】没有 token，放行");   // ← 加这行
            return true;
        }

        String token = authHeader.substring(7);
        System.out.println("【拦截器】token = " + token);   // ← 加这行

        try {
            Claims claims = jwtUtils.parseToken(token);
            System.out.println("【拦截器】claims = " + claims);   // ← 加这行

            Long userId = claims.get("userId", Long.class);
            System.out.println("【拦截器】解析出的 userId = " + userId);   // ← 加这行

            if (userId != null) {
                SysUser user = sysUserService.getById(userId);
                System.out.println("【拦截器】查到的 user = " + user);   // ← 加这行

                if (user != null) {
                    UserContext.setUserId(user.getKid());
                    UserContext.setInstId(user.getInstid());
                }
            }
        } catch (Exception e) {
            System.out.println("【拦截器】Token 解析失败：" + e.getMessage());
            e.printStackTrace();   // ← 加这行，打印完整堆栈
        }

        System.out.println("【拦截器】最终 userId = " + UserContext.getUserId());   // ← 加这行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束，清理 ThreadLocal，防止内存泄漏
        UserContext.clear();
    }
}