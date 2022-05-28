package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kivze.domain.HomeFunction;
import com.kivze.domain.HomeTitle;
import com.kivze.mapper.HomeFunctionMapper;
import com.kivze.mapper.HomeTitleMapper;
import com.kivze.service.HomeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service("HomeImageServiceImpl")
public class HomeImageServiceImpl implements HomeImageService {
    @Autowired
    HomeTitleMapper homeTitleMapper;

    @Autowired
    HomeFunctionMapper homeFunctionMapper;

    //获取首页的图片信息
    @Override
    public List<HomeTitle> getHomeImage() {
        List<HomeTitle> homeTitles = homeTitleMapper.selectList(new QueryWrapper<HomeTitle>());
        int i=0;
        for (HomeTitle homeTitle : homeTitles) {
            List<HomeFunction> homeFunctions = homeFunctionMapper.selectBatchIds(Arrays.asList(i + 1, i + 2, i + 3));
            homeTitle.setFunctionList(homeFunctions);
            i+=3;
        }
        return homeTitles;
    }


}
