package com.kivze.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kivze.domain.TmpCosSecre;
import com.kivze.domain.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface UserInfoService {
    //获取用户openId
    String getOpenIdAndSession(String code) throws JsonProcessingException;

    //获取COS临时id和key
    TmpCosSecre getCosIdAndKey();

    //获取用户id
    int getUserId(String openid);

    //向用户表添加该用户点赞过的帖子id
    int addPrizePost(int postId,int userId);

    //获取用户
    User getUserById(int userId);
}
