package com.kivze.util;


import lombok.Data;

//前后端统一协议
@Data
public class R {
    private Boolean flag;
    private Object data;
    private String msg;

    public R(){
    }

    public R(Boolean flag){
        this.flag = flag;
    }

    public R(Boolean flag, Object data){
        this.flag = flag;
        this.data = data;
    }

    public R(Boolean flag, String msg){
        this.flag = flag;
        this.msg = msg;
    }


}
