package com.kivze.service;

import com.github.pagehelper.PageInfo;
import com.kivze.domain.ChatPostInfo;
import com.kivze.domain.ChatPostReply;
import com.kivze.domain.PageQueryInfo;

import java.util.List;

public interface PostsInfoService {
    int addPostsInfo(ChatPostInfo chatPostInfo);

    PageInfo<ChatPostInfo> getNewAllPostsInfo(PageQueryInfo pageInfo);

    int addPostsReply(ChatPostReply chatPostReply);


}
