package com.kivze.service;

import com.kivze.domain.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface PicUpLoadService {
    PicUploadResult upload(MultipartFile uploadFile);

    boolean deletePic(String key);

}
