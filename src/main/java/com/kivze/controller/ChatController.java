package com.kivze.controller;

import com.github.pagehelper.PageInfo;
import com.kivze.domain.*;
import com.kivze.service.PostsFunctionCountService;
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
@RequestMapping("/kivze/chat")
public class ChatController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PostsInfoService postsInfoService;

    @Autowired
    private PostsReplyService postsReplyService;

    @Autowired
    private PostsFunctionCountService postsFunctionCountService;


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
    public R addPostsInfo(@RequestBody ChatPostInfo chatPostInfo){
        int[] ints = postsInfoService.addPostsInfo(chatPostInfo);
        //如果影响行数为0，代表未插入成功
        if (ints[0] == 0){
            return new R(false,"error");
        }
        //插入成功，将自增长的id值返回
        return new R(true,ints[1]);
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
    //根据id查询对应的PostsInfo动态信息
    @GetMapping("/getPostsInfoById/{id}")
    public R getPostsInfoById(@PathVariable Integer id){
        try {
            ChatPostInfo postsById = postsInfoService.getPostsById(id);
            return new R(true,postsById);
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

    //在存储动态转发，点赞数的表中，创建该表的记录
    @PostMapping("/addPostsCount")
    public R addPostsCount(@RequestBody Integer postId){
        int i = postsFunctionCountService.addPostsCount(postId);
        if (i == 0){
            return new R(false,"error");
        }
        return new R(true,"success");
    }

    //根据传入的帖子id，返回该帖子的点赞等数量
    @GetMapping("/getPostsFunctionCount/{postId}")
    public R getPostsFunctionCount(@PathVariable int postId){
        try {
            ChatPostFunctionCount postsCount = postsFunctionCountService.getPostsCount(postId);
            Map<String,Object> map = new HashMap<>();

            return new R(true,postsCount);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(false,"error");
        }
    }
    //增加回复数
    @PutMapping("/addReplyCount/{postId}")
    public R addReplyCount(@PathVariable int postId){
        int result = postsFunctionCountService.addReplyCount(postId);
        if (result == 0){
            return new R(false,"error");
        }
        return new R(true,"success");
    }

    //增加点赞数
    @PutMapping("/addPrizeCount/{postId}")
    public R addPrizeCount(@PathVariable int postId){
        int result = postsFunctionCountService.addPrizeCount(postId);
        if (result == 0){
            return new R(false,"error");
        }
        return new R(true,"success");
    }

    //更新帖子的点赞用户列表
    @PutMapping("/addPrizeUser/{postId}/{userId}")
    public R addPrizeUser(@PathVariable int postId,@PathVariable int userId){
        int result = postsFunctionCountService.addPrizeUser(postId, userId);
        if (result == 0){
            return new R(false,"error");
        }
        return new R(true,"success");
    }

    //更新转发数
    /*@PutMapping("/updateShareCount/{postId}/{shareCount}")
    public R updateShareCount(@PathVariable int postId,@PathVariable int shareCount){
        int result = postsFunctionCountService.updateShareCount(postId, shareCount);
        if (result == 0){
            return new R(false,"error");
        }
        return new R(true,"success");
    }*/
}
