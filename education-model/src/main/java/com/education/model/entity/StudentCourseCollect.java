package com.education.model.entity;

/**
 * 学员课程收藏表
 */
public class StudentCourseCollect extends BaseEntity<StudentCourseCollect> {

	private int courseId;
	private int studentId;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
}