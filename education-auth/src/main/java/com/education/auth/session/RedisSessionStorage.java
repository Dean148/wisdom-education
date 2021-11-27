package com.education.auth.session;

import com.education.common.cache.CacheBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 09:14
 * @since version 1.0.4
 */
public class RedisSessionStorage extends AbstractSessionStorage {

    private final CacheBean cacheBean;
    private static final String SESSION_KEY = "session:";

    public RedisSessionStorage(CacheBean cacheBean) {
        this.cacheBean = cacheBean;
    }

    @Override
    public void updateSession(UserSession userSession) {
        cacheBean.putValue(SESSION_KEY, userSession.getToken(), userSession);
    }

    @Override
    public UserSession getSession(String sessionId) {
        return cacheBean.get(SESSION_KEY, sessionId);
    }

    @Override
    public void deleteSession(String sessionId) {
        cacheBean.remove(SESSION_KEY, sessionId);
    }

    @Override
    public void refreshSessionTimeOut(UserSession userSession, long sessionTimeOut) {
        this.saveSession(userSession, sessionTimeOut);
    }

    @Override
    public void saveSession(UserSession userSession, long sessionTimeOut) {
        cacheBean.put(SESSION_KEY, userSession.getToken(), userSession, (int) sessionTimeOut);
    }

    @Override
    public List<UserSession> getActiveSessions() {
        List<UserSession> userSessionList = new ArrayList<>();
        Set<String> sessionIdList = (Set<String>) cacheBean.getKeys(SESSION_KEY);
        for (String sessionId : sessionIdList) {
            UserSession userSession = getSession(sessionId);
            if (userSession != null) {
                userSessionList.add(userSession);
            }
        }
        return userSessionList;
    }
}
