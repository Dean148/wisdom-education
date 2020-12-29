package com.education.business.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.cache.CacheBean;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/28 19:16
 */
public abstract class CrudService <M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Autowired
    @Qualifier("ehcacheBean")
    protected CacheBean cacheBean;

    public T selectFirst(QueryWrapper<T> queryWrapper) {
        queryWrapper.last(" limit 1");
        return super.getOne(queryWrapper);
    }

    public T selectByCache(String cacheName, Object key, QueryWrapper<T> queryWrapper) {
        T entity = cacheBean.get(cacheName, key);
        if (entity == null) {
            entity = this.selectFirst(queryWrapper);
            cacheBean.putValue(cacheName, key, entity);
        }
        return entity;
    }

    /**
     * 单表分页缓存查询
     * @param pageParam
     * @param entity
     * @return
     */
    public PageInfo<T> selectPageByCache(String cacheName, Object key, PageParam pageParam, T entity) {
        PageInfo<T> pageInfo = cacheBean.get(cacheName, key);
        if (pageInfo == null) {
            pageInfo = this.selectPage(pageParam, entity);
            cacheBean.putValue(cacheName, key, pageInfo);
        }
        return pageInfo;
    }

    public PageInfo<T> selectPageByCache(String cacheName, Object key, PageParam pageParam, Wrapper<T> queryWrapper) {
        PageInfo<T> pageInfo = cacheBean.get(cacheName, key);
        if (pageInfo == null) {
            pageInfo = this.selectPage(pageParam, queryWrapper);
            cacheBean.putValue(cacheName, key, pageInfo);
        }
        return pageInfo;
    }

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
     * @param pageParam
     * @param queryWrapper
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

    public List<T> selectListByCache(String cacheName, Object key, QueryWrapper<T> queryWrapper) {
        List<T> entity = cacheBean.get(cacheName, key);
        if (entity == null) {
            entity = this.list(queryWrapper);
            cacheBean.putValue(cacheName, key, entity);
        }
        return entity;
    }
}
