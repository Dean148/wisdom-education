package com.education.mapper.system;

import com.education.common.base.BaseMapper;
import com.education.common.model.ModelBeanMap;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 14:44
 */
public interface SystemAdminRoleMapper extends BaseMapper {

    /**
     * 获取管理员角色列表
     * @param adminId
     * @return
     */
    List<ModelBeanMap> findRoleListByAdminId(Integer adminId);

    /**
     * 根据管理员删除关联信息
     * @param adminId
     * @return
     */
    int deleteByAdminId(Integer adminId);
}
