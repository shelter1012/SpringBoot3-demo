package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("gpp_site_banner")
public class GppSiteBanner {

//    主键
    @TableId(type = IdType.AUTO)
    private Long kid;

//    轮播图地址
    private String imgUrl;

//    跳转连接
    private String linkUrl;

//    排序号
    private Integer sort;

//    是否展示
    private String isShow;

//    删除标记:0-正常,1-删除
    @TableLogic
    private String delFlag;

//    创建人ID
    private Long createUser;

//    创建时间
    private LocalDateTime createTime;

//    更新人ID
    private Long updateUser;

//    更新时间
    private LocalDateTime updateTime;

//    机构ID
    private String instid;
}
