package com.kivze.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivze.domain.User;
import com.kivze.mapper.UserMapper;
import com.kivze.service.PicUpLoadService;
import com.kivze.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/kivze")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("PicUpLoadServiceImpl")
    private PicUpLoadService picUpLoadService;

    @GetMapping("/test")
    public R projectTest(){
        return new R(true,"success");
    }

    @GetMapping("/test2/{id}")
    public R projectTest2(@PathVariable Integer id) throws JsonProcessingException {
        User user = userMapper.selectById(id);
        ObjectMapper mapper = new ObjectMapper();
        return new R(true,mapper.writeValueAsString(user));
    }

    @PostMapping
    public R projectTest3(@RequestParam("file") MultipartFile uploadFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return new R(true,mapper.writeValueAsString(picUpLoadService.upload(uploadFile)));
    }
}
