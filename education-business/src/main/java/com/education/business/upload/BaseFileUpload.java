package com.education.business.upload;
import cn.hutool.core.util.StrUtil;
import com.education.common.config.OssProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;

import java.net.URL;
import java.util.Date;


/**
 * @author zengjintao
 * @create_at 2021/10/31 15:25
 * @since version 1.0.3
 */
public abstract class BaseFileUpload implements FileUpload {

    protected String env;
    protected String applicationName;
    protected OssProperties ossProperties;
    protected String parentBucketName;

    public BaseFileUpload(OssProperties ossProperties, String applicationName) {
        this.ossProperties = ossProperties;
        this.applicationName = applicationName;
        this.parentBucketName = applicationName + StrUtil.DASHED + ossProperties.getAppId();
    }

    public BaseFileUpload(OssProperties ossProperties, String env, String applicationName) {
        this(ossProperties, applicationName);
        this.env = env;
        this.parentBucketName = applicationName + StrUtil.DASHED +
                env + StrUtil.DASHED + ossProperties.getAppId();
    }

    public void setParentBucketName(String parentBucketName) {
        this.parentBucketName = parentBucketName;
    }

    @Override
    public UploadResult createBucket(String name) {
        try {
            this.parentBucketName = name + StrUtil.DASHED +
                    env + StrUtil.DASHED + ossProperties.getAppId();
            this.doCreateBucket(parentBucketName);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    abstract void doCreateBucket(String name);
}
