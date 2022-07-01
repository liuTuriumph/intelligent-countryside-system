package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kivze.domain.*;
import com.kivze.mapper.PostsInfoMapper;
import com.kivze.mapper.PostsReplyMapper;
import com.kivze.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;


    //添加动态
    @Override
    public int[] addPostsInfo(ChatPostInfo chatPostInfo) {
        int result = postsInfoMapper.insert(chatPostInfo);
        Integer id = chatPostInfo.getId();
        //数组第一个存储插入操作影响的行数，第二个存储插入成功后自增长的id
        //前者用于判断是否插入成功，后者用于创建每个帖子转发数点赞数的功能表
        int[] postsRowsAndId = new int[2];
        postsRowsAndId[0]=result;
        postsRowsAndId[1]=id;
        return postsRowsAndId;
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

    //根据id获取动态信息
    @Override
    public ChatPostInfo getPostsById(Integer id) {
        QueryWrapper<ChatPostInfo> queryWrapper = new QueryWrapper<ChatPostInfo>();
        queryWrapper.eq("id",id);
        ChatPostInfo chatPostInfo = postsInfoMapper.selectOne(queryWrapper);
        return chatPostInfo;
    }

    //根据用户id查询其对应sendPostIds
    @Override
    public PageInfo<ChatPostInfo> getPostListById(Integer userId, PageQueryInfo pageInfo) {
        //根据id获取用户信息
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
        if (user == null || user.getSendPost()==null) return null;
        //根据用户的sendPostIds分页获取List<ChatPostInfo>集合
        PageHelper.startPage(pageInfo.getPagenum(),pageInfo.getPagesize());
        List<ChatPostInfo> chatPostInfos = postsInfoMapper.selectBatchIds(user.getSendPost());
        PageInfo<ChatPostInfo> info = new PageInfo<>(chatPostInfos);
        return info;
    }

    //根据用户id查询其对应sendPostIds
    @Override
    public PageInfo<ChatPostInfo> getPrizeListById(Integer userId, PageQueryInfo pageInfo) {
        //根据id获取用户信息
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
        if (user == null || user.getPrizePost()==null) return null;
        //根据用户的sendPostIds分页获取List<ChatPostInfo>集合
        PageHelper.startPage(pageInfo.getPagenum(),pageInfo.getPagesize());
        List<ChatPostInfo> chatPostInfos = postsInfoMapper.selectBatchIds(user.getPrizePost());
        PageInfo<ChatPostInfo> info = new PageInfo<>(chatPostInfos);
        return info;
    }




}
