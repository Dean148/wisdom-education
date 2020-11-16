package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("system_dict_value")
public class SystemDictValue extends BaseEntity<SystemDictValue> {

	@TableField("system_dict_id")
	private int systemDictId;
	private String value;
	private int code;
	@TableField("parent_id")
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