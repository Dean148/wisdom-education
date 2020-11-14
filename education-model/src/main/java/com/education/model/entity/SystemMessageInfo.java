package com.education.model.entity;

import java.util.Date;

public class  SystemMessageInfo{

	private int id;

	private int adminId;

	private int studentId;

	private String content;

	private Date createDate;

	private Date updateDate;

	private int courseId;

	private int testPaperInfoId;

	private long readFlag;

	public void setId(int id){
		this.id = id;
	}

	public  int  getId(){
		return	id;
	}
	public void setAdminId(int adminId){
		this.adminId = adminId;
	}

	public  int  getAdminId(){
		return	adminId;
	}
	public void setStudentId(int studentId){
		this.studentId = studentId;
	}

	public  int  getStudentId(){
		return	studentId;
	}
	public void setContent(String content){
		this.content = content;
	}

	public  String  getContent(){
		return	content;
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
	public void setCourseId(int courseId){
		this.courseId = courseId;
	}

	public  int  getCourseId(){
		return	courseId;
	}
	public void setTestPaperInfoId(int testPaperInfoId){
		this.testPaperInfoId = testPaperInfoId;
	}

	public  int  getTestPaperInfoId(){
		return	testPaperInfoId;
	}
	public void setReadFlag(long readFlag){
		this.readFlag = readFlag;
	}

	public  long  getReadFlag(){
		return	readFlag;
	}
}