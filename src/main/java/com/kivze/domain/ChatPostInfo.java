package com.kivze.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("chat_postsInfo")
public class ChatPostInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String openid;
    private String nickName;
    private String faceImage;
    private String content;
    private List<String> imagesList;
    private Date time;
}
