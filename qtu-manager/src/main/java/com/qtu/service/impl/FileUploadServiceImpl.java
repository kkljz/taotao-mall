package com.qtu.service.impl;

import com.qtu.service.FileUploadService;
import com.qtu.util.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu Shengkai
 * @create 2019-11-28 14:50
 */
@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${IMAGES_UPLOAD_BASE_PATH}")
    private String IMAGES_UPLOAD_BASE_PATH;//文件上传路径基础地址
    @Value("${IMAGES_BASE_URL}")
    private String IMAGES_BASE_URL; //文件服务器访问路径

    @Override
    public Map imagesUpload(MultipartFile fileUpload) {
        Map<String,Object> resultMap = null;
        try {
            resultMap = new HashMap<>();
            //生成文件名（生成id+上传文件名称）
            String name = IDUtils.genImageName();
            String imageName = name + fileUpload.getOriginalFilename();
            //得到上传路径（基础路径+/年/月/日）
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            String uploadPath = IMAGES_UPLOAD_BASE_PATH + File.separator + dateTime;
            File f1 = new File(uploadPath);
            if (!f1.exists()) {//判断文件夹是否存在，不存在就创建
                f1.mkdirs();
            }
            //路径+文件名（最终地址）
            String filePath = uploadPath + File.separator + imageName;
            fileUpload.transferTo(new File(filePath));//将上传的文件存储到指定位置

            //文件访问地址（文件服务器访问路径+/年/月/日+文件名）
            String fileUrl = IMAGES_BASE_URL + File.separator + dateTime+ File.separator + imageName;

            resultMap.put("error",0);
            resultMap.put("url",fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("error",0);
            resultMap.put("message","文件上传发生异常");
        }
        return resultMap;
    }
}
