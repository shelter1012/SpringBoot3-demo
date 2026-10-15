package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.GppSiteBanner;
import com.example.demo.service.GppSiteBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gpp/site/banner")
public class GppSiteBannerController {

    @Autowired
    private GppSiteBannerService gppSiteBannerService;

//    分页查询列表
    @GetMapping("/list")
    public Map<String,Object> list(
            @RequestParam(defaultValue = "") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            String imgUrl,
            String isShow,
            String instid,
            String sortOrder) {

        Map<String, Object> result = new HashMap<>();

        Page<GppSiteBanner> page = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<GppSiteBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(imgUrl != null && !imgUrl.isEmpty(),GppSiteBanner::getImgUrl,imgUrl);
        wrapper.eq(isShow !=null && !isShow.isEmpty(),GppSiteBanner::getIsShow,isShow);
        wrapper.eq(instid !=null && !instid.isEmpty(),GppSiteBanner::getInstid,instid);

        if("desc".equals(sortOrder)) {
            wrapper.orderByDesc(GppSiteBanner::getSort);
        }else {
            wrapper.orderByAsc(GppSiteBanner::getSort);
        }

        Page<GppSiteBanner> resultPage = gppSiteBannerService.page(page,wrapper);

        result.put("code","00000");
        result.put("msg","success");
        result.put("data",resultPage);
        return result;
    }

//    新增
    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody GppSiteBanner entity) {
        Map<String,Object> result = new HashMap<>();

        gppSiteBannerService.save(entity);

        result.put("code","00000");
        result.put("msg","新增成功");
        return result;
    }

//    编辑
    @PutMapping("/edit")
    public Map<String,Object> edit(@RequestBody GppSiteBanner entity) {
        Map<String, Object> result = new HashMap<>();

        gppSiteBannerService.updateById(entity);

        result.put("code", "00000");
        result.put("msg", "编辑成功");
        return result;
    }

//    删除
    @DeleteMapping("/remove/{ids}")
    public Map<String, Object> remove(@PathVariable String ids) {
        Map<String, Object> result = new HashMap<>();

        // 支持批量删除：ids 是 "1,2,3" 格式
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            gppSiteBannerService.removeById(Long.valueOf(id));
        }

        result.put("code", "00000");
        result.put("msg", "删除成功");
        return result;
    }
}
