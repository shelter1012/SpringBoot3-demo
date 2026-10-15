package com.example.demo.service;

import com.example.demo.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {

    private final Map<String, SysUser> tokenUserMap = new ConcurrentHashMap<>();

    public void put(String token, SysUser user) {
        tokenUserMap.put(token, user);
    }

    public SysUser get(String token) {
        return tokenUserMap.get(token);
    }

    public void remove(String token) {
        tokenUserMap.remove(token);
    }
}
