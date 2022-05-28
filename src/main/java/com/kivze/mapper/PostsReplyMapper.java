package com.kivze.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kivze.domain.ChatPostReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsReplyMapper extends BaseMapper<ChatPostReply> {
}
