package com.education.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021/10/31 15:30
 * @since version 1.0.3
 */
@ConfigurationProperties("upload.oss")
@Component
@Data
public class OssProperties {

    private String host;
    private String secretId;
    private String secretKey;
    private String platform;
    private String appId;
    private String region;
}
