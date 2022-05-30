package com.kivze.controller;

import com.github.pagehelper.PageInfo;
import com.kivze.domain.ChatPostInfo;
import com.kivze.domain.ChatPostReply;
import com.kivze.domain.PageQueryInfo;
import com.kivze.domain.TmpCosSecre;
import com.kivze.service.PostsInfoService;
import com.kivze.service.PostsReplyService;
import com.kivze.service.UserInfoService;
import com.kivze.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kivze/user")
public class ChatController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PostsInfoService postsInfoService;

    @Autowired
    private PostsReplyService postsReplyService;

    //返回openid
    @GetMapping("/getOpenId/{code}")
    public R getUserOpenId(@PathVariable String code){
        try {
            String openId = userInfoService.getOpenIdAndSession(code);
            return new R(true,openId);
        } catch (Exception e) {
            return new R(false,"error");
        }
    }
    //返回cos的临时密钥
    @GetMapping("/getCosSecre")
    public R getCosSecre(){
        try {
            TmpCosSecre cosIdAndKey = userInfoService.getCosIdAndKey();
            return new R(true,cosIdAndKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,"error");
        }
    }
    //将动态的内容存入数据库中
    @PostMapping("/addPostsInfo")
    public void addPostsInfo(@RequestBody ChatPostInfo chatPostInfo){
        int i = postsInfoService.addPostsInfo(chatPostInfo);
        System.out.println(i);
    }
    //返回PostsInfo动态信息
    @GetMapping("/getNewPostsInfo")
    public R getPostsInfo(PageQueryInfo pageInfo){
        try {
            PageInfo<ChatPostInfo> newAllPostsInfo = postsInfoService.getNewAllPostsInfo(pageInfo);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("postsInfoList",newAllPostsInfo.getList());
            map.put("total",newAllPostsInfo.getTotal());
            return new R(true,map);
        } catch (Exception e) {
            return new R(false,"error");
        }

    }
    //将回复的内容存入数据库中
    @PostMapping("/addPostsReply")
    public R addPostsReply(@RequestBody ChatPostReply chatPostReply){
        int i = postsInfoService.addPostsReply(chatPostReply);
        if (i==1){
            return new R(true);
        }else {
            return new R(false);
        }
    }
    //根据传入的动态id返回该动态回复信息列表
    @GetMapping("/getReply/{postId}")
    public R getPostsReply(@PathVariable int postId){
        try {
            List<ChatPostReply> postReply = postsReplyService.getPostReply(postId);
            return new R(true,postReply);
        } catch (Exception e) {
            return new R(false,"error");
        }
    }
    //根据传入的父级回复id返回该父级回复的子级回复信息列表
    @GetMapping("/getChildReply/{replyId}")
    public R getChildReply(@PathVariable int replyId){
        try {
            List<ChatPostReply> childReply = postsReplyService.getChildReply(replyId);
            return new R(true,childReply);
        } catch (Exception e) {
            return new R(false,"error");
        }
    }
}
