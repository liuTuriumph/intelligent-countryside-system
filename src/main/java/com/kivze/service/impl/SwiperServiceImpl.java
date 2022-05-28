package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kivze.domain.Swiper;
import com.kivze.mapper.SwiperMapper;
import com.kivze.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SwiperServiceImpl")
public class SwiperServiceImpl implements SwiperService {

    @Autowired
    private SwiperMapper swiperMapper;

    //获取所有轮播图数据
    @Override
    public List<Swiper> getAllSwiper() {
        List<Swiper> swipers = swiperMapper.selectList(new QueryWrapper<Swiper>());
        return swipers;
    }
}
