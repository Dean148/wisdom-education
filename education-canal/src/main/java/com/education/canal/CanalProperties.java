package com.education.canal;

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
public class CanalProperties {

    private static final Integer DEFAULT_PORT = 11111;
    private static final boolean DEFAULT_OPEN_FLAG = false;
    private static final String DEFAULT_DESTINATION =  "example";

    private boolean openFlag = DEFAULT_OPEN_FLAG;
    private Integer port = DEFAULT_PORT;
    private String destination = DEFAULT_DESTINATION;
    private String userName = "";
    private String password = "";
    private String host;

    public boolean isOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(boolean openFlag) {
        this.openFlag = openFlag;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
