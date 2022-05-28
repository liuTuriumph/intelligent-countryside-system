package com.kivze.domain;

import com.tencent.cloud.Credentials;
import lombok.Data;

@Data
public class TmpCosSecre {
    private Credentials credentials;
    private String startTime;
    private String expiredTime;
}
