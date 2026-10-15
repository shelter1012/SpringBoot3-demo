package com.example.demo.common;

public class UserContext {

//    ThreadLocal保存当前请求的用户信息
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> INST_ID = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void setInstId(String instId) {
        INST_ID.set(instId);
    }

    public static String getInstId() {
        return INST_ID.get();
    }

//    请求结束时清理,防止内存泄露
    public static void clear() {
        USER_ID.remove();
        INST_ID.remove();
    }
}
