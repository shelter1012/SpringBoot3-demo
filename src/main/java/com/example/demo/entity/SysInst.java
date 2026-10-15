package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_inst")
public class SysInst {
    @TableId
    private String instid;

    private String instName;

    private Integer sort;

//    是否启用
    private String status;

//    删除标记
    @TableLogic
    private String delFlag;

    private Long createUser;

    private LocalDateTime createTime;

    private Long updateUser;

    private LocalDateTime updateTime;
}
