package com.education.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.education.auth.session.SessionStorage;
import com.education.auth.session.UserSession;
import com.education.auth.token.TokenFactory;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:42
 * @since version 1.6.7
 */
public class AuthUtil {

    private static AuthRealmManager authRealmManager;

    private static final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public static <T> T login(LoginToken loginToken) {
        String loginType = loginToken.getLoginType();
        LoginAuthRealm loginAuthRealm = authRealmManager.getByLoginType(loginType);
        UserSession userSession = loginAuthRealm.doLogin(loginToken);
        AuthConfig authConfig = getAuthConfig();
        boolean flag = authConfig.isAllowMoreOnline();
        SessionStorage sessionStorage = authRealmManager.getSessionStorage();
        if (!flag) {
            ReentrantLock lock = getLock(userSession.getUserId());
            lock.lock();
            try {
                checkUserIsOnline(userSession.getUserId(), loginAuthRealm);
                long sessionTimeOut = loginAuthRealm.getSessionTimeOut(loginToken.isRemember());
                createUserSession(userSession, sessionStorage, sessionTimeOut);
            } finally {
                lock.unlock();
            }
        } else {
            long sessionTimeOut = loginAuthRealm.getSessionTimeOut(loginToken.isRemember());
            createUserSession(userSession, sessionStorage, sessionTimeOut);
        }
        loginAuthRealm.loadPermission(userSession);
        loginAuthRealm.onLoginSuccess(userSession);
        return (T) userSession;
    }

    public static ReentrantLock getLock(String userId) {
        ReentrantLock lock = lockMap.get(userId);
        if (lock == null) {
            synchronized (Object.class) {
                lock = new ReentrantLock();
                lockMap.putIfAbsent(userId, lock);
            }
        }
        return lock;
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

    private static void createUserSession(UserSession userSession, SessionStorage sessionStorage, long sessionTimeOut) {
        TokenFactory tokenFactory = authRealmManager.getTokenFactory();
        String token = tokenFactory.createToken(userSession.getUserId(), sessionTimeOut);
        userSession.setToken(token);
        sessionStorage.saveSession(userSession, sessionTimeOut);
    }

    /**
     * 刷新session 会话
     * @param userSession
     * @param sessionTimeOut
     */
    public static void refreshSession(UserSession userSession, long sessionTimeOut) {
        getSessionStorage().refreshSessionTimeOut(userSession, sessionTimeOut);
    }

    public static void updateSession(UserSession userSession) {
        getSessionStorage().updateSession(userSession);
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
        return request.getHeader(getAuthConfig().getHeaders());
    }
}
