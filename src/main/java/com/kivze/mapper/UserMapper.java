package com.kivze.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kivze.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
    int addPrizePost(User user);
}
