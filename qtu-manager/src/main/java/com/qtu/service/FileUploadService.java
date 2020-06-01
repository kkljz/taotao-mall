package com.qtu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-28 14:50
 */
public interface FileUploadService {
    /**
     * 上传图片
     * @param fileUpload
     * @return
     */
    Map imagesUpload(MultipartFile fileUpload);
}
