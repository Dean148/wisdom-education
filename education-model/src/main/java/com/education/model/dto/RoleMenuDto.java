package com.education.model.dto;

import com.education.model.entity.SystemMenu;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/15 16:22
 */
public class RoleMenuDto {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色权限列表
     */
    private List<SystemMenu> menuList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<SystemMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SystemMenu> menuList) {
        this.menuList = menuList;
    }
}
