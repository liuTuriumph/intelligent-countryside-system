package com.kivze.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("chat_postreply")
public class ChatPostReply {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer postId;
    private Integer replyId;
    private Boolean replyType;
    private String openid;
    private String nickName;
    private String faceImage;
    private String content;
    private String toOpenid;
    private String toNickName;
    private Date time;
}
