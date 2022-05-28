package com.kivze.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 首页功能的图片类
 */
@Data
@TableName("home_function")
public class HomeFunction {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String image_src;
}
