package com.education.mapper.system;

import com.education.common.base.BaseMapper;
import com.education.common.model.ModelBeanMap;
import com.education.model.entity.SystemAdmin;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 12:09
 */
public interface SystemAdminMapper extends BaseMapper<SystemAdmin> {

   /* *//**
     * 根据账号名获取管理员信息
     * @param loginName
     * @return
     *//*
    ModelBeanMap findByLoginName(String loginName);

    int updateByUserId(Map params);

    int deleteBySchoolId(Integer schoolId);

    List<SystemAdmin> list(SystemAdmin systemAdmin);

    int saveAdmin(@Param("params") SystemAdmin systemAdmin);*/

    List<SystemAdmin> pageList();
    // SystemAdmin selectAdmin(Integer adminId);
}
