package com.education.auth.session;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 09:48
 * @since version 1.0.4
 */
public abstract class AbstractSessionStorage implements SessionStorage {

    public static final long DEFAULT_GLOBAL_SESSION_TIMEOUT = 30 * 60 * 1000;
    private long sessionTimeOut = DEFAULT_GLOBAL_SESSION_TIMEOUT;

    @Override
    public void saveSession(UserSession userSession) {

    }

    @Override
    public void setSessionTimeOut(long sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    @Override
    public long getSessionTimeOut() {
        return sessionTimeOut;
    }
}
