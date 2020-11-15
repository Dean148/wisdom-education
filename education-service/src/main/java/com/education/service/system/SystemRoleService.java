package com.education.service.system;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.common.model.PageInfo;
import com.education.mapper.system.SystemRoleMapper;
import com.education.model.dto.RoleMenuDto;
import com.education.model.entity.SystemMenu;
import com.education.model.entity.SystemRole;
import com.education.model.entity.SystemRoleMenu;
import com.education.model.request.PageParam;
import com.education.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:25
 */
@Service
@Slf4j
public class SystemRoleService extends BaseService<SystemRoleMapper, SystemRole> {

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    public PageInfo<SystemRole> listPage(PageParam pageParam, SystemRole systemRole) {
        return selectPage(pageParam, Wrappers.query(systemRole));
    }

    /**
     * 根据管理员id 获取角色列表
     * @param adminId
     * @return
     */
    public List<SystemRole> findRoleListByAdminId(Integer adminId) {
        return baseMapper.findRoleListByAdminId(adminId);
    }

    /**
     * 保存角色权限
     * @param roleMenuDto
     */
    @Transactional
    public void saveRolePermission(RoleMenuDto roleMenuDto) {
        Integer roleId = roleMenuDto.getRoleId();
        systemRoleMenuService.deleteByRoleId(roleId); //先删除角色原有的权限
        List<SystemMenu> systemMenuList = roleMenuDto.getMenuList();
        if (ObjectUtils.isNotEmpty(systemMenuList)) {
            List<SystemRoleMenu> systemRoleMenuList = new ArrayList<>();
            systemMenuList.forEach(systemMenu -> {
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setMenuId(systemMenu.getId());
                systemRoleMenu.setRoleId(roleId);
                systemRoleMenuList.add(systemRoleMenu);
            });
            systemRoleMenuService.saveBatch(systemRoleMenuList);
        }
    }
/*
    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;


    @Override
    @Transactional
    public ResultCode deleteById(Integer id) {
        systemRoleMenuMapper.deleteByRoleId(id); // 删除角色菜单
        return super.deleteById(id);
    }

    *//**
     * 保存角色权限
     * @param roleMenuMap
     * @return
     *//*
    @Transactional
    public ResultCode savePermission(ModelBeanMap roleMenuMap) {
        try {
            systemRoleMenuMapper.deleteByRoleId(roleMenuMap.getInt("roleId")); //先删除角色原有的权限
            String permission = roleMenuMap.getStr("permission");
            if (ObjectUtils.isNotEmpty(permission)) {
                String permissions[] = permission.split(",");
                List<Map> list = new ArrayList<>();
                Integer roleId = roleMenuMap.getInt("roleId");
                for (String item : permissions) {
                    Map map = new HashMap<>();
                    map.put("menuId", item);
                    map.put("roleId", roleId);
                    list.add(map);
                }
                Map dataMap = new HashMap<>();
                dataMap.put("list", list);
                int result = systemRoleMenuMapper.batchSave(dataMap);
                if (result > 0) {
                    return new ResultCode(ResultCode.SUCCESS, "权限设置成功");
                }
                return new ResultCode(ResultCode.FAIL, "权限设置失败");
            }
            return new ResultCode(ResultCode.SUCCESS, "权限设置成功");
        } catch (Exception e) {
            log.error("权限设置异常", e);
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "权限设置异常"));
        }
    }*/



}
