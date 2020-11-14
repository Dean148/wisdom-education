package com.education.model.entity;

public class TestPaperQuestionInfo extends BaseEntity<TestPaperQuestionInfo> {

	private int questionInfoId;
	private int testPaperInfoId;
	private int mark;
	private int sort;

	public int getQuestionInfoId() {
		return questionInfoId;
	}

	public void setQuestionInfoId(int questionInfoId) {
		this.questionInfoId = questionInfoId;
	}

	public int getTestPaperInfoId() {
		return testPaperInfoId;
	}

	public void setTestPaperInfoId(int testPaperInfoId) {
		this.testPaperInfoId = testPaperInfoId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}