package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivze.domain.ChatPostFunctionCount;
import com.kivze.domain.CountTest;
import com.kivze.mapper.PostsFunctionCountMapper;
import com.kivze.service.PostsFunctionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostsFunctionCountServiceImpl implements PostsFunctionCountService {

    @Autowired
    private PostsFunctionCountMapper postsFunctionCountMapper;

    //创建帖子点赞等信息动态
    @Override
    public int addPostsCount(int postId) {
        //初始化数据
        ChatPostFunctionCount cpfc = new ChatPostFunctionCount();
        cpfc.setPostId(postId);
        cpfc.setShareCount(0);
        cpfc.setPrizeCount(0);
        cpfc.setReplyCount(0);
        List<String> list = new ArrayList<>();
        cpfc.setPrizeUser(list);
        int result = postsFunctionCountMapper.insert(cpfc);
        return result;
    }

    //获取点赞、转发等数量对象
    @Override
    public ChatPostFunctionCount getPostsCount(int postId) {
        QueryWrapper<ChatPostFunctionCount> qw = new QueryWrapper<>();
        qw.eq("postId",postId);
        ChatPostFunctionCount cpfc = postsFunctionCountMapper.selectOne(qw);
        return cpfc;
    }

    //更新帖子的点赞用户列表
    @Override
    public int addPrizeUser(int postId,int userId) {
        //先根据帖子id先获取prizeUser
        ChatPostFunctionCount postsCount = this.getPostsCount(postId);
        List<String> list = new ArrayList<>();
        if (postsCount.getPrizeUser() != null) list = new ArrayList<>(postsCount.getPrizeUser());
        list.add(Integer.toString(userId));
        //更新列表
        ChatPostFunctionCount cpfc = new ChatPostFunctionCount();
        cpfc.setPrizeUser(list);
        cpfc.setPostId(postId);
        int result = postsFunctionCountMapper.addPrizeUser(cpfc);
        return result;
    }

    //更新回复数
    @Override
    public int addReplyCount(int postId) {
        int result = this.postsFunctionCountMapper.addReplyCount(postId);
        return result;
    }

    //增加回复数
    @Override
    public int addPrizeCount(int postId) {
        int result = postsFunctionCountMapper.addPrizeCount(postId);
        return result;
    }
}
