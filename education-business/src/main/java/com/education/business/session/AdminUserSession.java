package com.education.business.session;

import com.education.auth.session.UserSession;
import com.education.common.constants.EnumConstants;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.MenuTree;
import com.education.model.entity.SystemAdmin;
import com.education.model.entity.SystemMenu;
import com.education.model.entity.SystemRole;

import java.util.List;

/**
 * admin 信息缓存实体
 * @author zengjintao
 * @create_at 2021年11月27日 0027 14:51
 * @since version 1.6.7
 */
public class AdminUserSession extends UserSession {

    private List<SystemRole> roleList;
    private List<SystemMenu> menuList;
    private List<MenuTree> menuTreeList; // 树形菜单列表
    private SystemAdmin systemAdmin;

    public void setSystemAdmin(SystemAdmin systemAdmin) {
        this.systemAdmin = systemAdmin;
    }


    public boolean isSuperAdmin() {
        if (ObjectUtils.isEmpty(systemAdmin)) {
            return false;
        }
        return systemAdmin.getSuperFlag() == EnumConstants.Flag.YES.getValue();
    }

    public SystemAdmin getSystemAdmin() {
        return systemAdmin;
    }

    public AdminUserSession(String userId) {
        super(userId);
    }

    public Integer getId() {
        return Integer.valueOf(super.getUserId());
    }

    public List<SystemRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SystemRole> roleList) {
        this.roleList = roleList;
    }

    public List<SystemMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SystemMenu> menuList) {
        this.menuList = menuList;
    }

    public List<MenuTree> getMenuTreeList() {
        return menuTreeList;
    }

    public void setMenuTreeList(List<MenuTree> menuTreeList) {
        this.menuTreeList = menuTreeList;
    }
}
