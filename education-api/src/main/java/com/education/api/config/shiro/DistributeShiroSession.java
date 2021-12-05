package com.education.api.config.shiro;

import com.education.common.cache.CacheBean;
import com.education.common.constants.CacheTime;
import com.education.common.constants.SystemConstants;
import com.education.common.model.UserHold;
import com.education.common.utils.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/7/3 20:12
 */
public class DistributeShiroSession extends AbstractSessionDAO {

    private CacheBean redisCacheBean;
    private static final String SESSION_KEY = SystemConstants.SESSION_KEY;
    private Integer expire = CacheTime.ONE_WEEK_SECOND; // 默认缓存7天


    /**
     * 设置session失效时间
     * @param expire
     */
    public void setExpire(Integer expire) {
        this.expire = expire;
    }


    public DistributeShiroSession (CacheBean redisCacheBean) {
        this.redisCacheBean = redisCacheBean;
    }

    // 创建sessionId
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        // 将session信息保存到redis
        this.saveSessionToCache(sessionId, session);
        return sessionId;
    }

    private void saveSessionToCache(Serializable sessionId, Session session) {
        if (ObjectUtils.isEmpty(sessionId)) {
            throw new NullPointerException("id argument cannot be null.");
        }
        Boolean rememberMe = UserHold.getRememberMe();
        if (rememberMe != null && rememberMe) {
            redisCacheBean.put(SESSION_KEY, sessionId, session, expire);
        }
        redisCacheBean.put(SESSION_KEY, sessionId, session, CacheTime.ONE_HOUR_SECOND);
    }

    /**
     * 用来获取session实例
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return redisCacheBean.get(SESSION_KEY, sessionId);
    }

    /**
     * 更新session实例信息
     * @param session
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSessionToCache(session.getId(), session);
    }

    /**
     * 用来删除session信息
     * @param session
     */
    @Override
    public void delete(Session session) {
        redisCacheBean.remove(SESSION_KEY, session.getId());
    }

    /**
     * 用来获取所有session集合
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Collection<String> keys = redisCacheBean.getKeys(SESSION_KEY);
        List<Session> sessionList = new ArrayList();
        if (ObjectUtils.isNotEmpty(keys)) {
            keys.forEach(key -> {
               Session session = redisCacheBean.get(SESSION_KEY, key);
               sessionList.add(session);
            });
            return Collections.unmodifiableCollection(sessionList);
        }
        return Collections.emptySet();
    }
}
