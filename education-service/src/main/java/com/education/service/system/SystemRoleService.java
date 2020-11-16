package com.education.service.system;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.common.model.PageInfo;
import com.education.mapper.system.SystemRoleMapper;
import com.education.model.dto.MenuTree;
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
        List<Integer> menuIds = roleMenuDto.getMenuIds();
        if (ObjectUtils.isNotEmpty(menuIds)) {
            List<SystemRoleMenu> systemRoleMenuList = new ArrayList<>();
            menuIds.forEach(menuId -> {
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setMenuId(menuId);
                systemRoleMenu.setRoleId(roleId);
                systemRoleMenuList.add(systemRoleMenu);
            });
            systemRoleMenuService.saveBatch(systemRoleMenuList);
        }
    }
}
