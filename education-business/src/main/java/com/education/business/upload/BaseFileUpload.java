package com.education.business.upload;
import cn.hutool.core.util.StrUtil;
import com.education.common.config.OssProperties;


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

    public BaseFileUpload(OssProperties ossProperties, String env, String applicationName) {
        this.ossProperties = ossProperties;
        this.env = env;
        this.applicationName = applicationName;
        this.parentBucketName = applicationName + StrUtil.DASHED +
                env + StrUtil.DASHED + ossProperties.getAppId();
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
