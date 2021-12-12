package com.education.common.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021/10/31 15:30
 * @since version 1.0.3
 */
@ConfigurationProperties("oss.upload")
@Component
@Data
public class OssProperties {

    private String host;
    private String secretId;
    private String secretKey;
    private String platform;
    private String appId;
    private String region;
    private String bucketName = StrUtil.EMPTY;
}
