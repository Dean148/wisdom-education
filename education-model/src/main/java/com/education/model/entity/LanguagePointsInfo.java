package com.education.model.entity;

/**
 * 知识点信息表实体类
 */
public class  LanguagePointsInfo{

	private String name;
	private int gradeType;
	private int subjectId;
	private int parentId;
	private long hasChildren;
	private int schoolType;
	private int sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGradeType() {
		return gradeType;
	}

	public void setGradeType(int gradeType) {
		this.gradeType = gradeType;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public long getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(long hasChildren) {
		this.hasChildren = hasChildren;
	}

	public int getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}