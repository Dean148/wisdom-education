package com.education.model.entity;

/**
 * 课程试题关联表实体类
 */
public class CourseQuestionInfo extends BaseEntity<CourseQuestionInfo> {

	private int sort;
	private int questionInfoId;
	private int courseId;
	private int mark;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getQuestionInfoId() {
		return questionInfoId;
	}

	public void setQuestionInfoId(int questionInfoId) {
		this.questionInfoId = questionInfoId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}