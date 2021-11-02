package com.education.common.upload;
import cn.hutool.core.util.StrUtil;
import com.education.common.config.OssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private final Logger logger = LoggerFactory.getLogger(BaseFileUpload.class);
    private String ossHost;

    public String getOssHost() {
        return ossHost;
    }

    public BaseFileUpload(OssProperties ossProperties, String applicationName) {
        this.ossProperties = ossProperties;
        this.applicationName = applicationName;
        this.parentBucketName = applicationName + StrUtil.DASHED + ossProperties.getAppId();
        this.ossHost = "https://" + parentBucketName;
    }

    public BaseFileUpload(OssProperties ossProperties, String env, String applicationName) {
        this(ossProperties, applicationName);
        this.env = env;
        this.parentBucketName = applicationName + StrUtil.DASHED +
                env + StrUtil.DASHED + ossProperties.getAppId();
        this.ossHost = "https://" + parentBucketName;
    }

    public void setParentBucketName(String parentBucketName) {
        this.parentBucketName = parentBucketName;
    }

    @Override
    public UploadResult createBucket(String name) {
        try {
            this.parentBucketName = name + StrUtil.DASHED +
                    env + StrUtil.DASHED + ossProperties.getAppId();
            return this.doCreateBucket(parentBucketName);
        } catch (Exception e) {
            logger.error(name + " 存储桶创建失败", e);
            return null;
        }
    }

    protected String generateFileKey(String path) {
        if (path.endsWith(StrUtil.SLASH)) {
            return path;
        }
        return path + StrUtil.SLASH;
    }

    abstract UploadResult doCreateBucket(String name);
}
