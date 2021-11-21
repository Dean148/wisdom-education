package com.education.common.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.education.common.config.OssProperties;
import com.jfinal.kit.FileKit;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author zengjintao
 * @create_at 2021年11月3日 0003 13:43
 * @since version 1.6.5
 */
public class LocalFileUpload extends BaseFileUpload {

    private final String uploadPath;
    private ThreadLocal<MultipartFile> multipartFileThreadLocal = new ThreadLocal<>();

    public LocalFileUpload(OssProperties ossProperties, String applicationName, String uploadPath) {
        super(ossProperties, applicationName);
        this.uploadPath = uploadPath;
    }

    public LocalFileUpload(OssProperties ossProperties, String env, String applicationName, String uploadPath) {
        super(ossProperties, env, applicationName);
        this.uploadPath = uploadPath;
    }

    public void setFile(MultipartFile file) {
        multipartFileThreadLocal.set(file);
    }

    public MultipartFile getFile() {
        return multipartFileThreadLocal.get();
    }

    @Override
    UploadResult doCreateBucket(String name) {
        File file = new File(uploadPath + StrUtil.SLASH + name);
        if (file.exists()) {
            file.mkdirs();
        }
        return new UploadResult();
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, File file) {
        return null;
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, File file) {
        return null;
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, InputStream inputStream) {
        FileUtil.writeFromStream(inputStream, new File(uploadPath + filePath + fileName));
        return new UploadResult();
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, InputStream inputStream) {
        return null;
    }

    @Override
    public void deleteObject(String filePath) {
        FileKit.delete(new File(filePath));
    }

    @Override
    public void deleteObject(String bucket, String filePath) {

    }
}
