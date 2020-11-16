package com.education.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.model.PageInfo;
import com.education.common.model.Tree;
import com.education.common.model.TreeData;
import com.education.common.utils.*;
import com.education.common.cache.CacheBean;
import com.education.model.dto.AdminUserSession;
import com.education.model.entity.BaseEntity;
import com.education.model.entity.SystemAdmin;
import com.education.model.request.PageParam;
import com.education.service.task.TaskManager;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


/**
 * service 基类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 10:40
 */
public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Autowired
    protected TaskManager taskManager;
    @Autowired
    @Qualifier("redisCacheBean")
    protected CacheBean cacheBean;
    @Resource
    protected CacheBean ehcacheBean;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 单表分页查询
     * @param pageParam
     * @param entity
     * @return
     */
    public PageInfo<T> selectPage(PageParam pageParam, T entity) {
        return this.selectPage(pageParam, Wrappers.query(entity));
    }

    /**
     * 条件列表分页查询
     * @param queryWrapper
     * @param <T>
     * @return
     */
    public PageInfo<T> selectPage(PageParam pageParam, Wrapper<T> queryWrapper) {
        PageInfo<T> pageInfo = new PageInfo();
        Integer pageNumber = pageParam.getPageNumber();
        Integer pageSize = pageParam.getPageSize();
        if (ObjectUtils.isEmpty(pageNumber) && ObjectUtils.isEmpty(pageSize)) {
            List<T> list = baseMapper.selectList(queryWrapper);
            pageInfo.setDataList(list);
            pageInfo.setTotal(ObjectUtils.isEmpty(list) ? 0 : list.size());
        } else {
            Page<T> page = new Page<>(pageNumber, pageSize);
            Page<T> listPage = super.page(page, queryWrapper);
            pageInfo.setTotal(listPage.getTotal());
            pageInfo.setDataList(page.getRecords());
        }
        return pageInfo;
    }

    public <V> PageInfo<V> selectPage(Page<V> page) {
        PageInfo<V> pageInfo = new PageInfo();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setDataList(page.getRecords());
        return pageInfo;
    }

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
}
