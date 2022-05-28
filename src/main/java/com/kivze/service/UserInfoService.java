package com.kivze.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kivze.domain.TmpCosSecre;

import java.util.Map;

public interface UserInfoService {
    //获取用户openId
    String getOpenIdAndSession(String code) throws JsonProcessingException;

    //获取COS临时id和key
    TmpCosSecre getCosIdAndKey();
}
