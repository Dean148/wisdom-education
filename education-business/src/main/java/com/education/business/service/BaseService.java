package com.education.business.service;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.auth.AuthUtil;
import com.education.business.session.AdminUserSession;
import com.education.business.session.StudentSession;
import com.education.business.task.TaskManager;
import com.education.common.cache.CacheBean;
import com.education.common.constants.AuthConstants;
import com.education.common.constants.CacheKey;
import com.education.common.model.JwtToken;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.StudentInfo;
import com.education.model.entity.SystemAdmin;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * service 基类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 10:40
 */
public abstract class BaseService<M extends BaseMapper<T>, T> extends CrudService<M, T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected TaskManager taskManager;
    @Autowired
    @Qualifier("redisCacheBean")
    protected CacheBean cacheBean;
    @Autowired
    protected HttpServletRequest request;
    @Resource
    protected RedisTemplate redisTemplate;
    @Resource
    protected RedissonClient redissonClient;
    @Resource
    private JwtToken jwtToken;


    public SystemAdmin getSystemAdmin() {
        AdminUserSession adminSession = getAdminUserSession();
        if (ObjectUtils.isNotEmpty(adminSession)) {
            return adminSession.getSystemAdmin();
        }
        return null;
    }

    public Integer getAdminUserId() {
        if (ObjectUtils.isNotEmpty(getSystemAdmin())) {
            return getSystemAdmin().getId();
        }
        return null;
    }

    /**
     * 获取管理员用户信息
     * @return
     */
    public AdminUserSession getAdminUserSession() {
        return (AdminUserSession) AuthUtil.getSession();
    }

    public StudentSession getStudentUserSession() {
        return (StudentSession) AuthUtil.getSession();
    }

    public StudentInfo getStudentInfo() {
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        return cacheBean.get(CacheKey.STUDENT_USER_INFO_CACHE, jwtToken.parseTokenToString(token));
    }


    public Integer getStudentId() {
        if (this.getStudentInfo() != null) {
            return this.getStudentInfo().getId();
        }
        return null;
    }
}
