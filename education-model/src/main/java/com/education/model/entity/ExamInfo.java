package com.education.model.entity;

/**
 * 考试信息表实体类
 */
public class ExamInfo extends BaseEntity<ExamInfo> {

	private int studentId;
	private int mark;
	private int testPaperInfoId;
	private long correctFlag;
	private int systemMark;
	private int teacherMark;
	private String examTime;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getTestPaperInfoId() {
		return testPaperInfoId;
	}

	public void setTestPaperInfoId(int testPaperInfoId) {
		this.testPaperInfoId = testPaperInfoId;
	}

	public long getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(long correctFlag) {
		this.correctFlag = correctFlag;
	}

	public int getSystemMark() {
		return systemMark;
	}

	public void setSystemMark(int systemMark) {
		this.systemMark = systemMark;
	}

	public int getTeacherMark() {
		return teacherMark;
	}

	public void setTeacherMark(int teacherMark) {
		this.teacherMark = teacherMark;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
}