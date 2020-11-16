package com.education.common.model.online;

import com.education.common.constants.EnumConstants;
import com.education.common.model.AdminUserSession;
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
    private String sessionId; // 服务器sessionId
    private EnumConstants.PlatformType platformType;
    private AdminUserSession adminUserSession;


    public OnlineUser(Integer userId, String sessionId, EnumConstants.PlatformType platformType) {
        this.userId = userId;
        this.platformType = platformType;
        this.sessionId = sessionId;
    }

    public Integer getAdminUserId() {
        if (ObjectUtils.isNotEmpty(adminUserSession)) {
            return adminUserSession.getUserId();
        }
        return null;
    }
}
