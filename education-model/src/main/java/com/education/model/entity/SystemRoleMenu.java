package com.education.model.entity;

public class SystemRoleMenu extends BaseEntity<SystemRoleMenu> {

	private int menuId;
	private int roleId;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}