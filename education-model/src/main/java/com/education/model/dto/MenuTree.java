package com.education.model.dto;

import com.education.common.model.TreeData;

import java.util.List;

/**
 * 菜单tree
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/14 14:57
 */
public class MenuTree extends TreeData<MenuTree> {

    private String url;
    private String permissions;
    private String icon;
    private List<Integer> parentIds; // 父级菜单集合

    public List<Integer> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Integer> parentIds) {
        this.parentIds = parentIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
