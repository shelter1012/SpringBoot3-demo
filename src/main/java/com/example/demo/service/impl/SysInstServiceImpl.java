package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.SysInst;
import com.example.demo.mapper.SysInstMapper;
import com.example.demo.service.SysInstService;
import org.springframework.stereotype.Service;

@Service
public class SysInstServiceImpl
        extends ServiceImpl<SysInstMapper, SysInst>
        implements SysInstService{
}
