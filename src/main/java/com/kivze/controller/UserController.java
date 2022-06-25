package com.kivze.controller;

import com.kivze.service.UserInfoService;
import com.kivze.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kivze/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;


    //返回用户openid
    @GetMapping("/getOpenId/{code}")
    public R getUserOpenId(@PathVariable String code){
        try {
            String openId = userInfoService.getOpenIdAndSession(code);
            return new R(true,openId);
        } catch (Exception e) {
            return new R(false,"error");
        }
    }

    //查询用户表，如果存在该用户返回id，不存在则创建用户并返回自增长的id
    @GetMapping("/getUserId/{openid}")
    public R getUserId(@PathVariable String openid){
        int userId = userInfoService.getUserId(openid);
        if (userId == -1){
            return new R(false,"error");
        }
        return new R(true,userId);
    }

    //向用户表添加该用户点赞过的帖子id
    @PutMapping("/addPrizePost/{postId}/{userId}")
    public R addPrizePost(@PathVariable int postId,@PathVariable int userId){
        int result = userInfoService.addPrizePost(postId, userId);
        if (result == 0){
            return new R(false,"error");
        }
        return new R(true,"success");
    }

}
