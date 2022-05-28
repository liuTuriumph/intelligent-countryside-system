package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kivze.domain.ChatPostInfo;
import com.kivze.domain.ChatPostReply;
import com.kivze.domain.PageQueryInfo;
import com.kivze.mapper.PostsInfoMapper;
import com.kivze.mapper.PostsReplyMapper;
import com.kivze.service.PostsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PostsInfoServiceImpl")
public class PostsInfoServiceImpl implements PostsInfoService {
    @Autowired
    private PostsInfoMapper postsInfoMapper;

    @Autowired
    private PostsReplyMapper postsReplyMapper;

    //添加动态
    @Override
    public int addPostsInfo(ChatPostInfo chatPostInfo) {
        if (chatPostInfo.getImagesList()!= null){
            int result = postsInfoMapper.insert(chatPostInfo);
            return result;
        }
        return 0;
    }

    //分页获取动态信息
    @Override
    public PageInfo<ChatPostInfo> getNewAllPostsInfo(PageQueryInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPagenum(), pageInfo.getPagesize());
        List<ChatPostInfo> chatPostInfos = postsInfoMapper.getAllNewPostsInfo();
        PageInfo<ChatPostInfo> info = new PageInfo<>(chatPostInfos);
        return info;
    }

    //添加动态的回复信息
    @Override
    public int addPostsReply(ChatPostReply chatPostReply) {
        if (chatPostReply != null){
            int result = postsReplyMapper.insert(chatPostReply);
            return result;
        }
        return 0;
    }

    //根据传入的动态id返回该动态回复信息列表
    @Override
    public List<ChatPostReply> getPostReply(int postId) {
        QueryWrapper<ChatPostReply> wrapper = new QueryWrapper<ChatPostReply>();
        wrapper.eq("postId",postId);
        List<ChatPostReply> chatPostReplyList = postsReplyMapper.selectList(wrapper);
        return chatPostReplyList;
    }


}
