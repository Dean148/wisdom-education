package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("system_admin")
public class SystemAdmin extends BaseEntity<SystemAdmin> {

	@TableField("login_name")
	private String loginName;
	private String password;
	private String encrypt;
	@TableField("disabled_flag")
	private boolean disabledFlag;
	private String loginIp;
	@TableField("login_count")
	private int loginCount;
	@TableField("school_id")
	private int schoolId;
	private String name;
	@TableField("last_login_time")
	private Date lastLoginTime;
	@TableField("create_type")
	private int createType;
	private String mobile;
	@TableField("super_flag")
	private boolean superFlag;
	@TableField("principal_flag")
	private boolean principalFlag;
	@TableField("account_type")
	private int accountType;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public boolean isDisabledFlag() {
		return disabledFlag;
	}

	public void setDisabledFlag(boolean disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isSuperFlag() {
		return superFlag;
	}

	public void setSuperFlag(boolean superFlag) {
		this.superFlag = superFlag;
	}

	public boolean isPrincipalFlag() {
		return principalFlag;
	}

	public void setPrincipalFlag(boolean principalFlag) {
		this.principalFlag = principalFlag;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
}