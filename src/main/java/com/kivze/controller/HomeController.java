package com.kivze.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivze.domain.HomeFunction;
import com.kivze.domain.HomeTitle;
import com.kivze.domain.Swiper;
import com.kivze.mapper.HomeFunctionMapper;
import com.kivze.service.HomeImageService;
import com.kivze.service.SwiperService;
import com.kivze.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kivze/images")
public class HomeController {

    @Autowired
    @Qualifier("SwiperServiceImpl")
    private SwiperService swiperService;

    @Autowired
    private HomeImageService homeImageService;


    //获取轮播图数据
    @GetMapping("/swiper")
    public R getSwiper(){
        try {
            List<Swiper> allSwiper = swiperService.getAllSwiper();
            /*ObjectMapper mapper = new ObjectMapper();
            return new R(true,mapper.writeValueAsString(allSwiper));*/
            return new R(true,allSwiper);
        } catch (Exception e) {
            return new R(false);
        }
    }

    //返回首页图片数据
    @GetMapping("/homeImage")
    public R getTitleImage(){
        try {
            List<HomeTitle> homeTitleImage = homeImageService.getHomeImage();
            return new R(true,homeTitleImage);
        } catch (Exception e) {
            return new R(false);
        }
    }

}
