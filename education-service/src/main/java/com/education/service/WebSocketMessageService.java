package com.education.service;

import com.education.common.constants.EnumConstants;
import com.education.common.utils.IpUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.RequestUtils;
import com.education.model.dto.OnlineUser;
import com.education.model.dto.OnlineUserManager;
import com.education.service.task.TaskManager;
import com.education.service.task.TaskParam;
import com.education.service.task.WebSocketMessageTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/11 14:57
 */
@Service
public class WebSocketMessageService {

    @Autowired
    private OnlineUserManager onlineUserManager;
    @Autowired
    private TaskManager taskManager;

    /**
     * 校验用户是否已在线
     * @param userId
     * @param platformType
     */
    public void checkOnlineUser(Integer userId, EnumConstants.PlatformType platformType) {
        OnlineUser onlineUser = onlineUserManager.getOnlineUser(userId);
        if (ObjectUtils.isNotEmpty(onlineUser)) {
            if (platformType != onlineUser.getPlatformType()) {
                return;
            }
            String sessionId = onlineUser.getSessionId();
            TaskParam taskParam = new TaskParam(WebSocketMessageTask.class);
            taskParam.put("sessionId", sessionId);
            taskParam.put("ip", IpUtils.getAddressIp(RequestUtils.getRequest()));
            taskManager.pushTask(taskParam);
        }
    }
}
