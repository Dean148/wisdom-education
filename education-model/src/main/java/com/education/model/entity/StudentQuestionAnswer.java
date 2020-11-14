package com.education.model.entity;

import java.util.Date;

public class  StudentQuestionAnswer{

	private int id;

	private int studentId;

	private int questionInfoId;

	private String answer;

	private long isRight;

	private Date createDate;

	private Date updateDate;

	private int testPaperInfoId;

	private String enclosure;

	private int mark;

	private String comment;

	private int questionPoints;

	private long correctFlag;

	private int courseId;

	private int correctStatus;

	public void setId(int id){
		this.id = id;
	}

	public  int  getId(){
		return	id;
	}
	public void setStudentId(int studentId){
		this.studentId = studentId;
	}

	public  int  getStudentId(){
		return	studentId;
	}
	public void setQuestionInfoId(int questionInfoId){
		this.questionInfoId = questionInfoId;
	}

	public  int  getQuestionInfoId(){
		return	questionInfoId;
	}
	public void setAnswer(String answer){
		this.answer = answer;
	}

	public  String  getAnswer(){
		return	answer;
	}
	public void setIsRight(long isRight){
		this.isRight = isRight;
	}

	public  long  getIsRight(){
		return	isRight;
	}
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public  Date  getCreateDate(){
		return	createDate;
	}
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public  Date  getUpdateDate(){
		return	updateDate;
	}
	public void setTestPaperInfoId(int testPaperInfoId){
		this.testPaperInfoId = testPaperInfoId;
	}

	public  int  getTestPaperInfoId(){
		return	testPaperInfoId;
	}
	public void setEnclosure(String enclosure){
		this.enclosure = enclosure;
	}

	public  String  getEnclosure(){
		return	enclosure;
	}
	public void setMark(int mark){
		this.mark = mark;
	}

	public  int  getMark(){
		return	mark;
	}
	public void setComment(String comment){
		this.comment = comment;
	}

	public  String  getComment(){
		return	comment;
	}
	public void setQuestionPoints(int questionPoints){
		this.questionPoints = questionPoints;
	}

	public  int  getQuestionPoints(){
		return	questionPoints;
	}
	public void setCorrectFlag(long correctFlag){
		this.correctFlag = correctFlag;
	}

	public  long  getCorrectFlag(){
		return	correctFlag;
	}
	public void setCourseId(int courseId){
		this.courseId = courseId;
	}

	public  int  getCourseId(){
		return	courseId;
	}
	public void setCorrectStatus(int correctStatus){
		this.correctStatus = correctStatus;
	}

	public  int  getCorrectStatus(){
		return	correctStatus;
	}
}