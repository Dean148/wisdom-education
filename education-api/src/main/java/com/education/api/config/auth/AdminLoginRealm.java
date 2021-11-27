package com.education.api.config.auth;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.auth.LoginAuthRealm;
import com.education.auth.LoginToken;
import com.education.auth.session.SessionStorage;
import com.education.business.service.system.SystemAdminService;
import com.education.business.session.AdminUserSession;
import com.education.business.task.TaskManager;
import com.education.business.task.TaskParam;
import com.education.business.task.WebSocketMessageListener;
import com.education.common.constants.CacheTime;
import com.education.common.constants.EnumConstants;
import com.education.common.enums.LoginEnum;
import com.education.common.exception.BusinessException;
import com.education.common.utils.IpUtils;
import com.education.common.utils.Md5Utils;
import com.education.common.utils.RequestUtils;
import com.education.model.entity.SystemAdmin;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 管理员登录认证
 * @author zengjintao
 * @create_at 2021年11月27日 0027 11:28
 * @since version 1.0.4
 */
@Component
public class AdminLoginRealm implements LoginAuthRealm<AdminUserSession> {

    @Resource
    private SystemAdminService systemAdminService;
    @Resource
    private TaskManager taskManager;

    @Override
    public AdminUserSession doLogin(LoginToken loginToken) {
        String password = loginToken.getPassword();
        LambdaQueryWrapper queryWrapper = Wrappers.<SystemAdmin>lambdaQuery()
                .eq(SystemAdmin::getLoginName, loginToken.getUsername());
        SystemAdmin systemAdmin = systemAdminService.getOne(queryWrapper);
        Assert.notNull(systemAdmin, () -> new BusinessException("用户名不存在"));
        password = Md5Utils.getMd5(password, systemAdmin.getEncrypt());
        if (password.equals(systemAdmin.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        AdminUserSession adminUserSession = new AdminUserSession(String.valueOf(systemAdmin.getId()));
        adminUserSession.setSystemAdmin(systemAdmin);
        return adminUserSession;
    }

    @Override
    public void loadPermission(AdminUserSession userSession) {
        systemAdminService.loadUserMenuAndPermission(userSession);
    }

    @Override
    public void onRejectSession(AdminUserSession userSession) {
        TaskParam taskParam = new TaskParam(WebSocketMessageListener.class);
        taskParam.put("sessionId", userSession.getToken());
        taskParam.put("message_type", EnumConstants.MessageType.STUDENT_LOGIN.getValue());
        taskParam.put("ip", IpUtils.getAddressIp(RequestUtils.getRequest()));
        taskManager.pushTask(taskParam);
    }

    @Override
    public String getLoginType() {
        return LoginEnum.ADMIN.getValue();
    }

    @Override
    public void beforeSaveSession(AdminUserSession userSession, boolean rememberMe, SessionStorage sessionStorage) {
        long timeOut;
        if (rememberMe) {
            // 缓存一周
            timeOut = CacheTime.ONE_WEEK_MILLIS;
        } else {
            // 缓存一小时
            timeOut = CacheTime.ONE_HOUR_MILLIS;
        }
        sessionStorage.setSessionTimeOut(timeOut);
    }
}
