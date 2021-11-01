package com.education.business.upload;

import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021/10/31 14:51
 * @since version 1.0.3
 */
public interface FileUpload {

    /**
     * 创建桶
     * @param name
     * @return
     */
    UploadResult createBucket(String name);

    UploadResult putObject(String filePath, String fileName);

    UploadResult putObject(String bucket, String filePath, String fileName);
}
