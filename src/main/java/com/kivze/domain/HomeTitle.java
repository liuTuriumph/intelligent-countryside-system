package com.kivze.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 首页标题的图片类
 */
@Data
@TableName("home_title")
public class HomeTitle {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String image_src;

    @TableField(select = false)
    private List<HomeFunction> functionList;
}
