package com.education.model.entity;

import java.util.Date;

public class SystemRole extends BaseEntity<SystemRole> {

	private String name;
	private String remark;
	private int createType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}
}