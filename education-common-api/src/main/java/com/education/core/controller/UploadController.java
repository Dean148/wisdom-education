package com.education.core.controller;

import cn.hutool.core.util.StrUtil;
import com.education.common.upload.FileUpload;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 文件上传接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 19:06
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${file.uploadPath}")
    private String baseUploadPath;

    @Resource
    private FileUpload fileUpload;

    // 上传文件类型
    private static final int VIDEO_FILE = 1;
    private static final int IMAGE_FILE = 2;
    private static final int OTHER_FILE = 3;

    private static final Set<String> videoTypes = new HashSet<String>() {
        {
            add("video/mp4");
            add("video/x-ms-wmv");
            add("video/mpeg4");
            add("video/avi");
            add("video/mpeg");
            add("video/3gp");
        }
    };

    /**
     * 文件上传api 接口
     * @param file
     * @param uploadFileType
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "{uploadFileType}", method = {RequestMethod.GET, RequestMethod.POST})
    public Map uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int uploadFileType)
            throws IOException {
        String result = null;
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String suffix = "." + FilenameUtils.getExtension(fileName);
        String message = StrUtil.EMPTY;
        Map resultMap = new HashMap<>();
        switch (uploadFileType) {
            case VIDEO_FILE :
                if (file.getSize() > 500 * 1024 * 1024) {
                    resultMap.put("code", ResultCode.FAIL);
                    resultMap.put("message", "视频大小不能超过500MB");
                    return resultMap;
                }
                result = beforeUploadVideo(contentType);
                message = "视频";
                break;
            case IMAGE_FILE :
                result = beforeUploadImage();
                fileName = ObjectUtils.generateUuId() + suffix;
                message = "图片";
                break;
            case OTHER_FILE :
                result = beforeUploadOtherFile();
                break;
        }

        if (ObjectUtils.isNotEmpty(result)) {
            try {
                fileUpload.putObject(result, fileName, file.getInputStream());
                resultMap.put("code", ResultCode.SUCCESS);
                resultMap.put("message", message + "上传成功");
                resultMap.put("url", result + fileName);
            } catch (Exception e) {
                resultMap.put("code", ResultCode.FAIL);
                resultMap.put("message", message + "文件上传失败");
                logger.error(message + "上传失败", e);
            }
        } else {
            resultMap.put("code", ResultCode.FAIL);
            resultMap.put("message", message + "文件格式错误,请更换文件");
            logger.warn(message + "文件格式错误,请更换文件");
        }
        return resultMap;
    }


    private String beforeUploadVideo(String contentType) {
        if (videoTypes.contains(contentType)) {
            return "/videos/" + ObjectUtils.generateFileByTime() + ObjectUtils.generateUuId() + StrUtil.SLASH;
        }
        return null;
    }

    private String beforeUploadImage() {
        return "/images/" + ObjectUtils.generateFileByTime();
    }

    private String beforeUploadOtherFile() {
        return "/others/" + ObjectUtils.generateFileByTime() + ObjectUtils.generateUuId() + StrUtil.SLASH;
    }
}
