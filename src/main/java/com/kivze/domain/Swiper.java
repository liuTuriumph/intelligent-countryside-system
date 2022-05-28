package com.kivze.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("home_swiper")
public class Swiper {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String image_src;
}
