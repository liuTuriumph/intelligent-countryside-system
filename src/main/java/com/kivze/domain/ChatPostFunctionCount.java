package com.kivze.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("chat_postfunctioncount")
public class ChatPostFunctionCount {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int postId;
    private int shareCount;
    private int prizeCount;
    private int replyCount;
    private String prizeUser;
}
