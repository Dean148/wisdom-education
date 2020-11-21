package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("test_paper_info")
public class TestPaperInfo extends BaseEntity<TestPaperInfo> {

	private String name;
	private String remark;
	private Integer mark;
	@TableField("school_type")
	private Integer schoolType;
	@TableField("grade_info_id")
	private Integer gradeInfoId;
	@TableField("publish_flag")
	private boolean publishFlag;
	@TableField("subject_id")
	private Integer subjectId;
	private Integer sort;
	@TableField("exam_number")
	private Integer examNumber;
	@TableField("correct_number")
	private Integer correctNumber;
	@TableField("exam_time")
	private Integer examTime;

	public Integer getExamTime() {
		return examTime;
	}

	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}

	public Integer getExamNumber() {
		return examNumber;
	}

	public void setExamNumber(Integer examNumber) {
		this.examNumber = examNumber;
	}

	public Integer getCorrectNumber() {
		return correctNumber;
	}

	public void setCorrectNumber(Integer correctNumber) {
		this.correctNumber = correctNumber;
	}

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

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public boolean isPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(boolean publishFlag) {
		this.publishFlag = publishFlag;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}