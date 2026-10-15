package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.GppSiteBanner;
import com.example.demo.mapper.GppSiteBannerMapper;
import com.example.demo.service.GppSiteBannerService;
import org.springframework.stereotype.Service;

@Service
public class GppSiteBannerServiceImpl
    extends ServiceImpl<GppSiteBannerMapper, GppSiteBanner>
    implements GppSiteBannerService{
}
