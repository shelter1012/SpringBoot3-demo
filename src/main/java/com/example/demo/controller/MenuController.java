package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    @GetMapping("/routes")
    public Map<String,Object> routes() {
        Map<String,Object> result = new HashMap<>();

        Map<String,Object> layout = new HashMap<>();
        layout.put("path","/");
        layout.put("component","Layout");
        layout.put("redirect","/dashboard");

//        首页
        Map<String,Object> dashboard = new HashMap<>();
        dashboard.put("path","dashboard");
        dashboard.put("name","Dashboard");
        dashboard.put("component","dashboard/index");
        Map<String, Object> dashMeta = new HashMap<>();
        dashMeta.put("title", "首页");
        dashMeta.put("icon", "dashboard");
        dashboard.put("meta", dashMeta);
        dashboard.put("children", new ArrayList<>());

//        轮播图管理
        Map<String,Object> banner = new HashMap<>();
        banner.put("path","banner");
        banner.put("name","SiteBanner");
        banner.put("component","site/banner/index");

        Map<String,Object> bannerMeta = new HashMap<>();
        bannerMeta.put("title","轮播图管理");
        bannerMeta.put("icon", "picture");
        banner.put("meta", bannerMeta);
        banner.put("children", new ArrayList<>());

//        组装Layout子菜单
        List<Map<String,Object>> layoutChildren = new ArrayList<>();
        layoutChildren.add(dashboard);
        layoutChildren.add(banner);
        layout.put("children",layoutChildren);
        layout.put("meta",dashMeta);

        List<Map<String,Object>> routes = new ArrayList<>();
        routes.add(layout);

        result.put("code", "00000");
        result.put("msg", "success");
        result.put("data", routes);
        return result;
    }
}
