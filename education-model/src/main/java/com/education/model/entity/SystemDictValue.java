package com.education.model.entity;

import java.util.Date;

public class SystemDictValue extends BaseEntity<SystemDictValue> {

	private int systemDictId;
	private String value;
	private int code;
	private int parentId;
	private int sort;

	public int getSystemDictId() {
		return systemDictId;
	}

	public void setSystemDictId(int systemDictId) {
		this.systemDictId = systemDictId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}