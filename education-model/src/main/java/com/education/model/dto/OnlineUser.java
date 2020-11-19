package com.education.model.dto;

import com.education.common.constants.EnumConstants;
import com.education.common.utils.ObjectUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/7 14:51
 */
@Data
public class OnlineUser implements Serializable {

    private Integer userId;
    private String token;
    private String sessionId; // 服务器sessionId
    private EnumConstants.PlatformType platformType;
    private AdminUserSession adminUserSession;

    public String getToken() {
        return token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public EnumConstants.PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(EnumConstants.PlatformType platformType) {
        this.platformType = platformType;
    }

    public AdminUserSession getAdminUserSession() {
        return adminUserSession;
    }

    public void setAdminUserSession(AdminUserSession adminUserSession) {
        this.adminUserSession = adminUserSession;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OnlineUser(Integer userId, String sessionId, EnumConstants.PlatformType platformType) {
        this.userId = userId;
        this.platformType = platformType;
        this.sessionId = sessionId;
    }

    public Integer getAdminUserId() {
        if (ObjectUtils.isNotEmpty(adminUserSession)) {
            return adminUserSession.getAdminId();
        }
        return null;
    }

}
