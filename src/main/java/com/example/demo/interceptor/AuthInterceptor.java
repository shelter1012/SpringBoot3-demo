package com.example.demo.interceptor;

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

        // 如果没带 token，直接放行（具体的权限校验留给 Controller 处理）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }

        String token = authHeader.substring(7);

        try {
            // 2. 解析 token
            Claims claims = jwtUtils.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            if (userId != null) {
                // 3. 查数据库获取用户信息
                SysUser user = sysUserService.getById(userId);

                if (user != null) {
                    // 4. 存入 ThreadLocal，供后续业务代码和 AutoFillHandler 使用
                    UserContext.setUserId(user.getKid());
                    UserContext.setInstId(user.getInstid());
                }
            }
        } catch (Exception e) {
            // token 无效或过期，不抛异常，让 Controller 去判断是否拦截
            System.out.println("【拦截器】Token 解析失败：" + e.getMessage());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束，必须清理 ThreadLocal，防止内存泄漏
        UserContext.clear();
    }
}