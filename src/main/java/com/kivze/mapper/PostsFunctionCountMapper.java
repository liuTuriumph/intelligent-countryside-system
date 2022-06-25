package com.kivze.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kivze.domain.ChatPostFunctionCount;
import com.kivze.domain.CountTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostsFunctionCountMapper extends BaseMapper<ChatPostFunctionCount> {
    int addReplyCount(int postId);

    int addPrizeCount(int postId);

    List<Integer> getPrizeUserByPostId(int postId);

    int addPrizeUser(ChatPostFunctionCount test);

}
