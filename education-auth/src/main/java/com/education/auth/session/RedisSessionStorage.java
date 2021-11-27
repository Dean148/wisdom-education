package com.education.auth.session;

import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 09:14
 * @since version 1.0.4
 */
public class RedisSessionStorage extends AbstractSessionStorage {

    @Override
    public void updateSession(UserSession userSession) {

    }

    @Override
    public UserSession getSession(String sessionId) {
        return null;
    }

    @Override
    public void deleteSession(String sessionId) {

    }

    @Override
    public List<UserSession> getActiveSessions() {
        return super.getActiveSessions();
    }
}
