package com.kivze.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kivze.domain.ChatPostFunctionCount;

import java.util.List;

public interface PostsFunctionCountService {
    int addPostsCount(int postId);

    ChatPostFunctionCount getPostsCount(int postId);

    int addPrizeUser(int postId,int userId);

    int addReplyCount(int postId);

    int addPrizeCount(int postId);
}
