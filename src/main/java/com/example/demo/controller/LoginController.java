package com.example.demo.controller;

import com.example.demo.entity.SysUser;
import com.example.demo.service.SysUserService;

import com.example.demo.common.utils.JwtUtils;
import com.example.demo.service.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> param) {
        String username = param.get("username");
        String password = param.get("password");

        Map<String,Object> result = new HashMap<>();

//        1.查询用户
        SysUser user = sysUserService.getByUsername(username);
        if(user == null){
            result.put("code","A0401");
            result.put("msg","账号不存在");
        }else {
            //        2.验证密码
            if(!encoder.matches(password,user.getPassword())) {
                result.put("code","A0401");
                result.put("msg","密码错误");
            }else {
                String token = jwtUtils.generateToken(user.getKid(),user.getUsername());
//                返回数据
                result.put("code","00000");
                result.put("msg","登陆成功");
                result.put("token",token);
                result.put("realName",user.getRealName());
            }
        }
        return result;
    }
}
