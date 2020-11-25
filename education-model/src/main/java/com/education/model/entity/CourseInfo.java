package com.education.model.entity;


public class CourseInfo extends BaseEntity<CourseInfo> {

	private String name;
	private Integer gradeInfoId;
	private Integer schoolType;
	private Integer subjectId;
	private String represent;
	private String code;
	private Integer sort;
	private Integer status;
	private Integer studyNumber;
	private Integer recommendIndexFlag;


	public String getRepresent() {
		return represent;
	}

	public void setRepresent(String represent) {
		this.represent = represent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStudyNumber() {
		return studyNumber;
	}

	public void setStudyNumber(Integer studyNumber) {
		this.studyNumber = studyNumber;
	}

	public Integer getRecommendIndexFlag() {
		return recommendIndexFlag;
	}

	public void setRecommendIndexFlag(Integer recommendIndexFlag) {
		this.recommendIndexFlag = recommendIndexFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}