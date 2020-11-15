package com.education.model.entity;

/**
 * 课程信息表实体类
 */
public class CourseInfo extends BaseEntity<CourseInfo> {

	private String name;
	private int gradeType;
	private int schoolType;
	private int subjectId;
	private String code;
	private String sort;
	private int parentId;
	private String headImg;
	private String represent;
	private long recommendIndexFlag;
	private int studyNumber;
	private int status;

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

	public int getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getRepresent() {
		return represent;
	}

	public void setRepresent(String represent) {
		this.represent = represent;
	}

	public long getRecommendIndexFlag() {
		return recommendIndexFlag;
	}

	public void setRecommendIndexFlag(long recommendIndexFlag) {
		this.recommendIndexFlag = recommendIndexFlag;
	}

	public int getStudyNumber() {
		return studyNumber;
	}

	public void setStudyNumber(int studyNumber) {
		this.studyNumber = studyNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}