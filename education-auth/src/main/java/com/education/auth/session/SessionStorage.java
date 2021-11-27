package com.education.auth.session;

import java.util.Collections;
import java.util.List;

/**
 * session 会话缓存接口
 * @author zengjintao
 * @create_at 2021年11月25日 0025 17:14
 * @since version 1.0.4
 */
public interface SessionStorage {

    /**
     * 创建会话id
     * @return
     */
    default String createSessionId(String token) {
        return token;
    }

    /**
     * 保存session
     * @param userSession
     */
    void saveSession(UserSession userSession);

    /**
     * 获取会话列表
     * @return
     */
    default List<UserSession> getActiveSessions() {
        return Collections.emptyList();
    }

    /**
     * 更新会话缓存
     * @param userSession
     */
    void updateSession(UserSession userSession);

    /**
     * 获取用户会话
     * @param sessionId
     * @return
     */
    UserSession getSession(String sessionId);

    /**
     * 设置会话有效期
     * @param sessionTimeOut
     */
    void setSessionTimeOut(long sessionTimeOut);

    /**
     * 获取session 有效期
     * @return
     */
    default long getSessionTimeOut() {
        return 0;
    }

    /**
     * 删除session 会话
     * @param sessionId
     */
    void deleteSession(String sessionId);

    /**
     * 刷新 session会话有效期
     * @param userSession
     * @param sessionTimeOut
     */
    void refreshSessionTimeOut(UserSession userSession, long sessionTimeOut);
}
