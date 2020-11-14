package com.education.model.entity;

import java.util.Date;

public class TestPaperInfo extends BaseEntity<TestPaperInfo> {

	private String name;
	private String remark;
	private int mark;
	private int schoolType;
	private int gradeType;
	private int subjectId;
	private int sort;
	private int examTime;
	private int examNumber;
	private int createType;
	private int correctNumber;
	private long publishFlag;

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

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getExamTime() {
		return examTime;
	}

	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}

	public int getExamNumber() {
		return examNumber;
	}

	public void setExamNumber(int examNumber) {
		this.examNumber = examNumber;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public int getCorrectNumber() {
		return correctNumber;
	}

	public void setCorrectNumber(int correctNumber) {
		this.correctNumber = correctNumber;
	}

	public long getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(long publishFlag) {
		this.publishFlag = publishFlag;
	}
}