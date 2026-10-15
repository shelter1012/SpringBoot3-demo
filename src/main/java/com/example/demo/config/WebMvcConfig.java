package com.example.demo.config;

import com.example.demo.intercepter.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.access-prefix}")
    private String accessPrefix;

    @Autowired
    private AuthInterceptor authInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//      把URL前缀/uploads/**映射到本地磁盘的upload-path目录
        registry.addResourceHandler(accessPrefix + "/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")// 拦截所有请求
                .excludePathPatterns("/login");// 排除登录接口
    }
}
