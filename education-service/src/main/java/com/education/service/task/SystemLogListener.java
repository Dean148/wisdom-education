package com.education.service.task;
import com.education.common.constants.EnumConstants;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.AdminUserSession;
import com.education.model.entity.SystemLog;
import com.education.service.system.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * 异步记录系统日志，提升系统响应速度
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/24 20:54
 */
@Component
public class SystemLogListener implements TaskListener {


    @Autowired
    private SystemLogService systemLogService;

    @Override
    public void onMessage(TaskParam taskParam) {
        SystemLog systemLog = new SystemLog();
        AdminUserSession adminUserSession = (AdminUserSession) taskParam.get("adminUserSession");
        if (ObjectUtils.isNotEmpty(adminUserSession)) {
            systemLog.setUserId(adminUserSession.getAdminId());
            systemLog.setOperationName(adminUserSession.getSystemAdmin().getLoginName());
        }
        systemLog.setCreateDate(new Date());
        systemLog.setPlatformType(EnumConstants.PlatformType.WEB_ADMIN.getValue());
        long startTime = taskParam.getLong("startTime");
        systemLog.setRequestTime((System.currentTimeMillis() - startTime) + "ms");
        systemLog.setRequestUrl(taskParam.getStr("request_url"));
        systemLog.setException(taskParam.getStr("exception"));
        systemLog.setIp(taskParam.getStr("ip"));
        systemLog.setMethod(taskParam.getStr("method"));
        systemLog.setParams(taskParam.getStr("params"));
        systemLog.setOperationDesc(taskParam.getStr("operation_desc"));
        systemLogService.save(systemLog);
    }
}
