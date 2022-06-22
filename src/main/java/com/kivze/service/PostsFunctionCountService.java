package com.kivze.service;

import com.kivze.domain.ChatPostFunctionCount;

public interface PostsFunctionCountService {
    int addPostsCount(int postId);

    ChatPostFunctionCount getPostsCount(int postId);

    int updateShareCount(int postId,int shareCount);

    int updatePrizeCount(int postId,int prizeCount);

    int updateReplyCount(int postId,int replyCount);
}
