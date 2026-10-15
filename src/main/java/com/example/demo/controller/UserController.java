package com.example.demo.controller;

import com.example.demo.entity.SysUser;
//import com.example.demo.service.TokenStore;
import com.example.demo.common.utils.JwtUtils;
import com.example.demo.service.SysUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/me")
    public Map<String,Object> me(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Map<String,Object> result = new HashMap<>();

        if(authHeader == null || !authHeader.startsWith("Bearer")){
            result.put("code","A0230");
            result.put("msg","未登录");
            return result;
        }

        String token = authHeader.substring(7);
//        解析token
        Claims claims;
        try {
            claims = jwtUtils.parseToken(token);
        } catch (Exception e) {
            result.put("code","A0230");
            result.put("msg","Token无效或已过期");
            return result;
        }
//        从token里拿userId
        Long userId = claims.get("userId", Long.class);

//        再去数据库查完整用户信息
        SysUser user = sysUserService.getById(userId);
        if(user == null) {
            result.put("code","A0230");
            result.put("msg","用户不存在");
            return result;
        }




        Map<String,Object> data = new HashMap<>();
        data.put("userId",String.valueOf(user.getKid()));
        data.put("username",user.getUsername());
        data.put("nickname",user.getRealName());
        data.put("avatar","");
        data.put("roles",List.of("ROOT"));
        data.put("perms",List.of("*:*:*"));

        result.put("code","00000");
        result.put("msg","success");
        result.put("data",data);
        return result;
    }
}
