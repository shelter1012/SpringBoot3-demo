package com.example.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.stereotype.Component;
import com.example.demo.common.UserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Component
public class AutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        插入时,自动填充create_user和update_user
        Long userId = UserContext.getUserId();
        if(userId != null) {
            this.strictInsertFill(metaObject, "createUser", Long.class, userId);
            this.strictInsertFill(metaObject, "updateUser", Long.class, userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        更新时候,自动填充update_user
        Long userId = UserContext.getUserId();
        if(userId != null) {
            this.strictUpdateFill(metaObject, "updateUser", Long.class, userId);
        }
    }
}
