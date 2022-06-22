package com.kivze;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kivze.domain.ChatPostFunctionCount;
import com.kivze.domain.ChatPostInfo;
import com.kivze.domain.ChatPostReply;
import com.kivze.domain.PageQueryInfo;
import com.kivze.mapper.*;
import com.kivze.service.PicUpLoadService;
import com.kivze.service.PostsInfoService;
import com.kivze.service.PostsReplyService;
import com.kivze.service.SwiperService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest
public class springTest {
    @Autowired
    private UserMapper userMapper;

    @Value("${spring.tengxun.SecretId}")
    private String secretId;

    @Value("${spring.tengxun.SecretKey}")
    private String secretKey;

    @Value("${spring.tengxun.region}")
    private String region;

    @Value("${spring.tengxun.bucketName}")
    private String bucketName;

    @Value("${spring.tengxun.url}")
    private String path;

    @Value("${MyAddress}")
    private String myAddress;

    private COSClient cosClient;

    @Autowired
    @Qualifier("PicUpLoadServiceImpl")
    private PicUpLoadService picUpLoadService;

    @Autowired
    private SwiperMapper swiperMapper;

    @Autowired
    private SwiperService swiperService;

    @Autowired
    private PostsInfoService postsInfoService;

    @Autowired
    private PostsInfoMapper mapper;

    @Autowired
    private PostsReplyMapper postsReplyMapper;

    @Autowired
    private PostsReplyService postsReplyService;


    @Autowired
    private PostsInfoMapper postsInfoMapper;

    @Autowired
    private PostsFunctionCountMapper postsFunctionCountMapper;


    @BeforeEach
    public void init(){
        //1.初始化用户的身份信息
        COSCredentials cred = new BasicCOSCredentials(this.secretId,this.secretKey);
        //2.设置bucket的地区
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        //3.生成cos客户端
        COSClient cosClient = new COSClient(cred,clientConfig);
        this.cosClient = cosClient;
    }
    /*@Test
    void test12(){
        UpdateWrapper<ChatPostFunctionCount> uw = new UpdateWrapper<>();
        uw.eq("postId",30).set("shareCount",1);
        int result = this.postsFunctionCountMapper.update(null, uw);
        System.out.println(result);
    }*/

    /*@Test
    void test11(){
        QueryWrapper<ChatPostFunctionCount> qw = new QueryWrapper<>();
        qw.eq("postId",28);
        ChatPostFunctionCount cpfc = postsFunctionCountMapper.selectOne(qw);
        System.out.println(cpfc);
    }*/

    /*@Test
    void test10(){
        ChatPostFunctionCount cpfc = new ChatPostFunctionCount();
        cpfc.setPostId(1145);
        cpfc.setShareCount(0);
        cpfc.setPrizeCount(0);
        int result = postsFunctionCountMapper.insert(cpfc);
    }*/

    /*@Test
    void test9(){
        int id=18;
        QueryWrapper<ChatPostInfo> queryWrapper = new QueryWrapper<ChatPostInfo>();
        queryWrapper.eq("id",id);
        postsInfoMapper.selectOne(queryWrapper);
    }*/

    /*@Test
    void test8(){
        *//*List<ChatPostReply> chatPostReplyList = postsReplyMapper.selectList(new QueryWrapper<ChatPostReply>());
        System.out.println(chatPostReplyList.get(0).getReplyType());*//*
        List<ChatPostReply> childReply = postsReplyService.getChildReply(1);
        System.out.println(childReply);
    }*/

    //测试分页
    /*@Test
    void test5(){
        PageHelper.startPage(1, 3);
        List<ChatPostInfo> allNewPostsInfo = mapper.getAllNewPostsInfo();
        PageInfo<ChatPostInfo> info = new PageInfo<>(allNewPostsInfo);
        System.out.println(info);
        for (ChatPostInfo chatPostInfo : info.getList()) {
            System.out.println(chatPostInfo);
        }

    }*/

    //测试获取动态内容
    /*@Test
    void tet3(){
        List<ChatPostInfo> allNewPostsInfo = mapper.getAllNewPostsInfo();
        System.out.println(allNewPostsInfo);
    }*/

    //测试cos生成临时密钥
    /*@Test
    public void test5() {
        Calendar now = Calendar.getInstance();
        System.out.println();
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {

            // 云 api 密钥 SecretId
            config.put("secretId", secretId);
            // 云 api 密钥 SecretKey
            config.put("secretKey", secretKey);


            // 设置域名
            //config.put("host", "sts.internal.tencentcloudapi.com");

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);

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
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);

            Response response = CosStsClient.getCredential(config);
            System.out.println(response.credentials.tmpSecretId);
            System.out.println(response.credentials.tmpSecretKey);
            System.out.println(response.credentials.sessionToken);
            System.out.println(System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }*/

        //测试根据code获取小程序用户openID
    /*@Test
    public void test3(){
        String code ="003sRDll2WKB994w8inl2zhWzy1sRDln";
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
        System.out.println(forObject);

        *//*System.out.println("sr========"+sr);
        //解析相应内容（转换成json对象）
        JSONObject json =JSON.parseObject(sr);
        System.out.println("json============"+json);
        //获取会话密钥（session_key）json.get("session_key").toString();
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");*//*
        *//*map.put("session_key",session_key);
        map.put("openid",openid);
        return map;*//*
    }*/

    /*//查询桶列表
    @Test
    void queryBucket(){
        List<Bucket> buckets = cosClient.listBuckets();
        System.out.println(buckets);
    }
    //上传文件
    @Test
    void upLoad(){
        //指定上传文件
        File localFile = new File("C:\\Users\\14616\\Desktop\\素材\\jack.jpg");
        //指定上传桶
        String bucketName = this.bucketName;
        //要上传到COS上的对象键
        String key = "exampleobject.jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,key,localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult);
    }

    @Test
    void getObject(){
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置 bucket 名称
        listObjectsRequest.setBucketName(this.bucketName);
        // prefix 表示列出的 object 的 key 以 prefix 开始
        listObjectsRequest.setPrefix("images/swiper/");
        // 设置最大遍历出多少个对象, 一次 listobject 最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        listObjectsRequest.setDelimiter("/");
        ObjectListing objectListing = cosClient.listObjects(listObjectsRequest);
        for (COSObjectSummary cosObjectSummary : objectListing.getObjectSummaries()) {
            // 对象的路径 key
            String key = cosObjectSummary.getKey();
            // 对象的 etag
            String etag = cosObjectSummary.getETag();
            // 对象的长度
            long fileSize = cosObjectSummary.getSize();
            // 对象的存储类型
            String storageClass = cosObjectSummary.getStorageClass();
            System.out.println("key:" + key + "; etag:" + etag + "; fileSize:" + fileSize + "; storageClass:" + storageClass);
        }
    }
    @Test
    void test1() throws IOException {
        File localFile = new File("C:\\Users\\14616\\Desktop\\素材\\jack.jpg");
        FileInputStream input = new FileInputStream(localFile);
        MultipartFile multipartFile = new MockMultipartFile("file",localFile.getName(),"image/jpeg", IOUtils.toByteArray(input));
        picUpLoadService.upload(multipartFile);
    }

    @Test
    //将cos中轮播图的文件路径存储到表中
    void test4(){
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置 bucket 名称
        listObjectsRequest.setBucketName(this.bucketName);
        // prefix 表示列出的 object 的 key 以 prefix 开始
        listObjectsRequest.setPrefix("images/swiper/");
        // 设置最大遍历出多少个对象, 一次 listobject 最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        listObjectsRequest.setDelimiter("/");
        ObjectListing objectListing = cosClient.listObjects(listObjectsRequest);
        for (COSObjectSummary cosObjectSummary : objectListing.getObjectSummaries()) {
            // 对象的长度
            long fileSize = cosObjectSummary.getSize();
            if (fileSize == 0){continue;}
            // 对象的路径 key
            String key = cosObjectSummary.getKey();
            // 存储到表中
            Swiper swiper = new Swiper();
            swiper.setImage_src(myAddress+"/"+key);
            int insert = swiperMapper.insert(swiper);
            System.out.println(insert);

        }
    }

    @Test
    void testt(){
        List<Swiper> allSwiper = swiperService.getAllSwiper();
        System.out.println(allSwiper);
        Swiper swiper = swiperMapper.selectById(1);
        System.out.println(swiper);
    }*/

}
