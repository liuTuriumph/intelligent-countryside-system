package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kivze.domain.ChatPostReply;
import com.kivze.mapper.PostsReplyMapper;
import com.kivze.service.PostsReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostsReplyServiceImpl implements PostsReplyService {
    @Autowired
    private PostsReplyMapper postsReplyMapper;

    //根据传入的动态id返回该动态回复信息列表
    @Override
    public List<ChatPostReply> getPostReply(int postId) {
        QueryWrapper<ChatPostReply> wrapper = new QueryWrapper<ChatPostReply>();
        wrapper.eq("postId",postId);
        wrapper.eq("replyType",true);
        List<ChatPostReply> chatPostReplyList = postsReplyMapper.selectList(wrapper);
        return chatPostReplyList;
    }

    //根据传入的父级回复id返回该父级回复的子级回复信息列表
    @Override
    public List<ChatPostReply> getChildReply(int replyId) {
        QueryWrapper<ChatPostReply> wrapper = new QueryWrapper<ChatPostReply>();
        wrapper.eq("replyId",replyId);
        wrapper.eq("replyType",false);
        List<ChatPostReply> chatPostReplyList = postsReplyMapper.selectList(wrapper);
        return chatPostReplyList;
    }
}
