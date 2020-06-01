package com.qtu.controller;

import com.qtu.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-28 14:47
 */
@Controller
@RequestMapping("/imgs")
public class ImagesUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 图片上传
     * @param uploadFile
     * @return
     */
    @ResponseBody
    @RequestMapping("/upload")
    public Map imagesUpload(MultipartFile uploadFile){
        return fileUploadService.imagesUpload(uploadFile);
    }
}
