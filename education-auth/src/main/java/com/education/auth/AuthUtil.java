package com.education.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.education.auth.exception.AuthException;
import com.education.auth.session.SessionStorage;
import com.education.auth.session.UserSession;
import com.education.auth.token.TokenFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:42
 * @since version 1.0.4
 */
public class AuthUtil implements ApplicationContextAware {

    private static AuthRealmManager authRealmManager;

    public static void login(LoginToken loginToken) throws AuthException {
        String loginType = loginToken.getLoginType();
        LoginAuthRealm loginAuthRealm = authRealmManager.getByLoginType(loginType);
        UserSession userSession = loginAuthRealm.doLogin(loginToken);
        AuthConfig authConfig = getAuthConfig();
        boolean flag = authConfig.isAllowMoreOnline();
        synchronized (Object.class) {
            // 禁止账号同时在线
            if (!flag) {
                checkUserIsOnline(userSession.getUserId(), loginAuthRealm);
            }
            createUserSession(userSession, loginAuthRealm);
            loginAuthRealm.loadPermission(userSession);
            loginAuthRealm.onLoginSuccess(userSession);
        }
    }

    public static UserSession getSessionByToken(String token) {
        return getSessionStorage().getSession(token);
    }

    /**
     * 校验当前用户是否已登录
     * @param userId
     */
    private static void checkUserIsOnline(String userId, LoginAuthRealm loginAuthRealm) {
        List<UserSession> list = getSessionStorage().getActiveSessions();
        if (CollUtil.isNotEmpty(list)) {
            UserSession userSession = list.stream()
                    .filter(session -> session.getUserId().equals(userId))
                    .findAny().orElseGet(() -> null);
            if (userSession != null) {
                // 删除上一个用户会话信息
                getSessionStorage().deleteSession(userSession.getToken());
                loginAuthRealm.onRejectSession(userSession);
            }
        }
    }

    public static AuthConfig getAuthConfig() {
        return authRealmManager.getAuthConfig();
    }

    public static void setAuthRealmManager(AuthRealmManager authRealmManager) {
        AuthUtil.authRealmManager = authRealmManager;
    }

    private static void createUserSession(UserSession userSession, LoginAuthRealm loginAuthRealm) {
        TokenFactory tokenFactory = authRealmManager.getTokenFactory();
        String token = tokenFactory.createToken();
        userSession.setToken(token);
        SessionStorage sessionStorage = authRealmManager.getSessionStorage();
        sessionStorage.setSessionTimeOut(tokenFactory.getTokenExpirationTime());
        sessionStorage.saveSession(userSession);
        loginAuthRealm.onLoginSuccess(userSession);
        System.out.println(JSONUtil.toJsonStr(getSessionByToken(token)));
    }

    public static UserSession getSession() {
        String token = getTokenValue();
        SessionStorage sessionStorage = authRealmManager.getSessionStorage();
        return sessionStorage.getSession(token);
    }

    public static boolean hasPermission(String permission) {
        UserSession userSession = getSession();
        List<String> permissionList = userSession.getPermissionList();
        return permissionList.contains(permission);
    }


    public static SessionStorage getSessionStorage() {
        return authRealmManager.getSessionStorage();
    }

    public static void logout() {
        String token = getTokenValue();
        if (StrUtil.isNotBlank(token)) {
            getSessionStorage().deleteSession(token);
        }
    }

    public static String getTokenValue(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("Authorization");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        authRealmManager = applicationContext.getBean(AuthRealmManager.class);
        if (authRealmManager == null) {
            throw new RuntimeException("Can Not Find Bean " + AuthRealmManager.class);
        }
    }
}
