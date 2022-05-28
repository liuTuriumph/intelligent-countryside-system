package com.kivze.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivze.domain.OpenIds;
import com.kivze.domain.TmpCosSecre;
import com.kivze.service.UserInfoService;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public String getOpenIdAndSession(String code) throws JsonProcessingException {

            //小程序唯一标识   (在微信小程序管理后台获取)
            String wxspAppid = "wx18f02a4c8621d8cf";
            //小程序的 app secret (在微信小程序管理后台获取)
            String wxspSecret = "8bd5a8d7b079ff8401c7ce32c5145a7d";
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
}
