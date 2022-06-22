package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kivze.domain.ChatPostFunctionCount;
import com.kivze.mapper.PostsFunctionCountMapper;
import com.kivze.service.PostsFunctionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    //更新转发数
    @Override
    public int updateShareCount(int postId,int shareCount) {
        UpdateWrapper<ChatPostFunctionCount> uw = new UpdateWrapper<>();
        uw.eq("postId",postId).set("shareCount",shareCount);
        int result = this.postsFunctionCountMapper.update(null, uw);
        return result;
    }
    //更新点赞数
    @Override
    public int updatePrizeCount(int postId,int prizeCount) {
        UpdateWrapper<ChatPostFunctionCount> uw = new UpdateWrapper<>();
        uw.eq("postId",postId).set("prizeCount",prizeCount);
        int result = this.postsFunctionCountMapper.update(null, uw);
        return result;
    }
    //更新回复数
    @Override
    public int updateReplyCount(int postId,int replyCount) {
        UpdateWrapper<ChatPostFunctionCount> uw = new UpdateWrapper<>();
        uw.eq("postId",postId).set("replyCount",replyCount);
        int result = this.postsFunctionCountMapper.update(null, uw);
        return result;
    }
}
