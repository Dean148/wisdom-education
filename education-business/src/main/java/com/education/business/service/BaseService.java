package com.education.business.service;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.business.task.TaskManager;
import com.education.common.cache.CacheBean;
import com.education.common.constants.AuthConstants;
import com.education.common.constants.CacheKey;
import com.education.common.constants.SystemConstants;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.AdminUserSession;
import com.education.model.dto.StudentInfoSession;
import com.education.model.entity.StudentInfo;
import com.education.model.entity.SystemAdmin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
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

    /**
     * 更新shiro 缓存中的用户信息，避免由于redis 缓存导致获取用户信息不一致问题
     * @param adminUserSession
     */
    public void updateShiroCacheUserInfo(AdminUserSession adminUserSession) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        //realName认证信息的key，对应的value就是认证的user对象
        String realName = principals.getRealmNames().iterator().next();
        //创建一个PrincipalCollection对象
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(adminUserSession, realName);
        // 调用subject的runAs方法，把新的PrincipalCollection放到session里面
        subject.runAs(newPrincipalCollection);
    }

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
        Subject subject = SecurityUtils.getSubject();
        AdminUserSession userSession = (AdminUserSession)subject.getPrincipal();
        if (ObjectUtils.isNotEmpty(userSession)) {
            return userSession;
        }
        return null;
    }



    public StudentInfoSession getStudentInfoSession() {
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        return cacheBean.get(CacheKey.STUDENT_USER_INFO_CACHE, token);
    }

    public StudentInfo getStudentInfo() {
        StudentInfoSession studentInfoSession = getStudentInfoSession();
       if (ObjectUtils.isNotEmpty(studentInfoSession)) {
           return studentInfoSession.getStudentInfo();
       }
       return null;
    }

    public Integer getStudentId() {
        if (this.getStudentInfo() != null) {
            return this.getStudentInfo().getId();
        }
        return null;
    }
}
