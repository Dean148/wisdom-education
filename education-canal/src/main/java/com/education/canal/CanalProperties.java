package com.education.canal;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * canal 属性配置
 * @author zengjintao
 * @version 1.6.3
 * @create_at 2021年9月9日 0009 13:39
 */
@ConfigurationProperties(prefix = "canal")
@Component
@Data
public class CanalProperties {

    private static final Integer DEFAULT_PORT = 1111;
    private static final boolean DEFAULT_OPEN_FLAG = false;
    private static final String DEFAULT_DESTINATION =  "example";

    private boolean openFlag = DEFAULT_OPEN_FLAG;
    private Integer port = DEFAULT_PORT;
    private String destination = DEFAULT_DESTINATION;
    private String userName = "";
    private String password = "";
}
