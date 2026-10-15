package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.SysInst;
import com.example.demo.service.SysInstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/insts")
public class SysInstController {

    @Autowired
    private SysInstService sysInstService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();

        // 查所有启用的机构，按 sort 升序
        LambdaQueryWrapper<SysInst> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysInst::getStatus, "1");
        wrapper.orderByAsc(SysInst::getSort);

        List<SysInst> instList = sysInstService.list(wrapper);

        // 转成前端下拉框需要的格式：{value, label}
        List<Map<String, String>> data = new ArrayList<>();
        for (SysInst inst : instList) {
            Map<String, String> item = new HashMap<>();
            item.put("value", inst.getInstid());
            item.put("label", inst.getInstName());
            data.add(item);
        }

        result.put("code", "00000");
        result.put("msg", "success");
        result.put("data", data);
        return result;
    }
}