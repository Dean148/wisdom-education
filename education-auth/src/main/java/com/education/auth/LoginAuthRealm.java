package com.education.auth;

import com.education.auth.session.UserSession;

/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:25
 * @since version 1.0.4
 */
public interface LoginAuthRealm<T extends UserSession> {

    long DEFAULT_GLOBAL_SESSION_TIMEOUT = 60 * 60 * 1000;

    /**
     * 用户登陆
     * @param loginToken
     * @return
     */
    T doLogin(LoginToken loginToken);

    /**
     * 获取登陆类型
     * @return
     */
    default String getLoginType() {
        return getClass().getSimpleName();
    }

    /**
     * 登录成功回调方法
     * @param userSession
     */
    default void onLoginSuccess(T userSession) {
        System.out.println("账号:【" + userSession.getUserId() + "】登录成功");
    }

    /**
     * 退出成功回调事件
     * @param userSession
     */
    default void onLogoutSuccess(T userSession) {
        System.out.println("账号:【" + userSession.getUserId() + "】注销成功");
    }

    /**
     * 用户账号被顶下线事件回调
     * @param userSession
     */
    default void onRejectSession(T userSession) {
        System.out.println("剔除会话id:【" + userSession.getToken() + "】成功");
    }

    /**
     * 登录失败事件回调
     * @param username
     */
    default void onLoginFail(String username, Exception e) {
        System.err.println("账号:【" + username + "】登录失败");
        e.printStackTrace();
    }


    /**
     * 加载账号权限
     * @param userSession
     */
    default void loadPermission(T userSession) {
        System.out.println("加载权限方法");
    }

    /**
     * 登录之后可设置token 会话有效期
     * @param rememberMe
     */
    default long getSessionTimeOut(boolean rememberMe) {
        return DEFAULT_GLOBAL_SESSION_TIMEOUT;
    }
}
