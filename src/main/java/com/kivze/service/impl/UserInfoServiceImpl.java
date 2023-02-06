package com.kivze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivze.domain.OpenIds;
import com.kivze.domain.TmpCosSecre;
import com.kivze.domain.User;
import com.kivze.mapper.UserMapper;
import com.kivze.service.UserInfoService;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl  implements UserInfoService {

    @Value("${spring.tengxun.SecretId}")
    private String secretId;

    @Value("${spring.tengxun.SecretKey}")
    private String secretKey;

    @Value("${spring.tengxun.region}")
    private String region;

    @Value("${spring.tengxun.bucketName}")
    private String bucketName;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getOpenIdAndSession(String code) throws JsonProcessingException {

            //小程序唯一标识   (在微信小程序管理后台获取)
            String wxspAppid = "XXXX";
            //小程序的 app secret (在微信小程序管理后台获取)
            String wxspSecret = "XXXX";
            //授权（必填）
            String grant_type = "authorization_code";
            //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
            //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
            //请求参数
            String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
            //发送请求
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?" + params, String.class);
            //将json转换为对象
            ObjectMapper objectMapper = new ObjectMapper();
            OpenIds openIds = objectMapper.readValue(forObject, OpenIds.class);

            return openIds.getOpenid();
    }

    @Override
    public TmpCosSecre getCosIdAndKey() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {

            // 云 api 密钥 SecretId
            config.put("secretId", secretId);
            // 云 api 密钥 SecretKey
            config.put("secretKey", secretKey);

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds",1800);

            // 换成你的 bucket
            config.put("bucket", bucketName);
            // 换成 bucket 所在地区
            config.put("region", region);

            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[] {
                    "*",
            });

            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload",
                    //取消分块上传操作
                    "name/cos:AbortMultipartUpload"
            };
            config.put("allowActions", allowActions);

            Response response = CosStsClient.getCredential(config);
            TmpCosSecre tmpCosSecre = new TmpCosSecre();
            tmpCosSecre.setCredentials(response.credentials);
            tmpCosSecre.setStartTime(String.valueOf(System.currentTimeMillis()/1000));
            tmpCosSecre.setExpiredTime(String.valueOf(System.currentTimeMillis()/1000+1800));

            return tmpCosSecre;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

    @Override
    public int getUserId(String openid) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("openid",openid);
        User user = userMapper.selectOne(qw);
        //如果用户已经创建，返回该用户id
        if (user != null) return user.getId();
        //用户不存在，创建该用户
        User user1 = new User();
        user1.setOpenid(openid);
        List<String> list = new ArrayList<>();
        user1.setPrizePost(list);
        user1.setSendPost(list);
        int result = userMapper.insert(user1);
        //创建失败返回-1
        if (result == 0) return -1;
        //创建成功，返回创建后自增长的id
        return user1.getId();
    }

    @Override
    public User getUserById(int userId) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",userId);
        User user = userMapper.selectOne(qw);
        return user;
    }

    @Override
    public int addPrizePost(int postId, int userId) {
        //先获取数据库中的prizePost
        User userById = this.getUserById(userId);
        List<String> prizePost = userById.getPrizePost();
        List<String> list = new ArrayList<>();
        if (prizePost != null) list=new ArrayList<>(prizePost);
        list.add(Integer.toString(postId));
        //更新prizePost
        User user = new User();
        user.setId(userId);
        user.setPrizePost(list);
        int reslut = userMapper.addPrizePost(user);
        return reslut;
    }


}
