package com.education.service.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.mapper.system.SystemAdminRoleMapper;
import com.education.model.entity.SystemAdminRole;
import com.education.service.BaseService;
import org.springframework.stereotype.Service;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 14:44
 */
@Service
public class SystemAdminRoleService extends BaseService<SystemAdminRoleMapper, SystemAdminRole> {

    public int deleteByAdminId(Integer adminId) {
        return baseMapper.delete(new QueryWrapper<SystemAdminRole>().eq("admin_id", adminId));
    }
}
