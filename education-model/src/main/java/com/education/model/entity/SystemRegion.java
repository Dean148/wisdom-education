package com.education.model.entity;

import java.util.Date;

public class  SystemRegion{

	private int id;

	private String parentCode;

	private String code;

	private String name;

	private String fullName;

	private Date createDate;

	private Date updateDate;

	public void setId(int id){
		this.id = id;
	}

	public  int  getId(){
		return	id;
	}
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	public  String  getParentCode(){
		return	parentCode;
	}
	public void setCode(String code){
		this.code = code;
	}

	public  String  getCode(){
		return	code;
	}
	public void setName(String name){
		this.name = name;
	}

	public  String  getName(){
		return	name;
	}
	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public  String  getFullName(){
		return	fullName;
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
}