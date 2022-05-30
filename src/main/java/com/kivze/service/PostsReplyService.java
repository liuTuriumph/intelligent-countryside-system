package com.kivze.service;

import com.kivze.domain.ChatPostReply;

import java.util.List;

public interface PostsReplyService {
    List<ChatPostReply> getPostReply(int postId);

    List<ChatPostReply> getChildReply(int replyId);
}
