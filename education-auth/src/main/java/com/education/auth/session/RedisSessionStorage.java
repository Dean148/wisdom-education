package com.education.auth.session;

import cn.hutool.core.util.StrUtil;
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
        String cacheName = getCacheName(userSession.getLoginType());
        cacheBean.putValue(cacheName, userSession.getToken(), userSession);
    }

    @Override
    public UserSession getSession(String sessionId) {
        return getSession(sessionId, null);
    }

    @Override
    public UserSession getSession(String sessionId, String loginType) {
        return cacheBean.get(getCacheName(loginType), sessionId);
    }

    @Override
    public void deleteSession(String sessionId) {
        deleteSession(sessionId, null);
    }

    @Override
    public void deleteSession(String sessionId, String loginType) {
        cacheBean.remove(getCacheName(loginType), sessionId);
    }

    private String getCacheName(String loginType) {
        String cacheName = SESSION_KEY;
        if (StrUtil.isNotBlank(loginType)) {
            cacheName += loginType;
        }
        return cacheName;
    }


    @Override
    public void saveSession(UserSession userSession, long sessionTimeOut) {
        String cacheName = getCacheName(userSession.getLoginType());
        cacheBean.put(cacheName, userSession.getToken(), userSession, (int) sessionTimeOut);
    }

    @Override
    public List<UserSession> getActiveSessions() {
        return getActiveSessions(null);
    }

    @Override
    public List<UserSession> getActiveSessions(String loginType) {
        List<UserSession> userSessionList = new ArrayList<>();
        Set<String> sessionIdList = (Set<String>) cacheBean.getKeys(getCacheName(loginType));
        for (String sessionId : sessionIdList) {
            UserSession userSession = getSession(sessionId);
            if (userSession != null) {
                userSessionList.add(userSession);
            }
        }
        return userSessionList;
    }
}
