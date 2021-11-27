package com.education.auth.session;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 09:14
 * @since version 1.0.4
 */
public class RedisSessionStorage extends AbstractSessionStorage {

    private final RedisTemplate redisTemplate;

    public RedisSessionStorage(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void saveSession(UserSession userSession) {
        super.saveSession(userSession);
    }


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
    public void refreshSessionTimeOut(UserSession userSession, long sessionTimeOut) {

    }

    @Override
    public List<UserSession> getActiveSessions() {
        return super.getActiveSessions();
    }
}
