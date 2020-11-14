package com.education.model.entity;

import java.util.Date;

public class  StudyHistory{

	private int id;

	private int subjectId;

	private Date createDate;

	private Date update;

	private int studentId;

	private int studyTime;

	private Date updateDate;

	private int courseId;

	private Date startDate;

	private Date endDate;

	public void setId(int id){
		this.id = id;
	}

	public  int  getId(){
		return	id;
	}
	public void setSubjectId(int subjectId){
		this.subjectId = subjectId;
	}

	public  int  getSubjectId(){
		return	subjectId;
	}
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public  Date  getCreateDate(){
		return	createDate;
	}
	public void setUpdate(Date update){
		this.update = update;
	}

	public  Date  getUpdate(){
		return	update;
	}
	public void setStudentId(int studentId){
		this.studentId = studentId;
	}

	public  int  getStudentId(){
		return	studentId;
	}
	public void setStudyTime(int studyTime){
		this.studyTime = studyTime;
	}

	public  int  getStudyTime(){
		return	studyTime;
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
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}

	public  Date  getStartDate(){
		return	startDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public  Date  getEndDate(){
		return	endDate;
	}
}