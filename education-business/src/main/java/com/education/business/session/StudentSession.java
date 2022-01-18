package com.education.business.session;

import com.education.auth.session.UserSession;


/**
 * 学员信息会话缓存
 * @author zengjintao
 * @create_at 2021年11月27日 0027 14:56
 * @since version 1.0.4
 */
public class StudentSession extends UserSession {

    private String name;
    private Integer age;
    private Integer sex;
    private String address;
    private String mobile;
    private String headImg;
    private Integer gradeInfoId;
    private String loginName;
    private String gradeInfoName;
    private String socketSessionId;


    public void setSocketSessionId(String socketSessionId) {
        this.socketSessionId = socketSessionId;
    }

    public String getSocketSessionId() {
        return socketSessionId;
    }

    public String getGradeInfoName() {
        return gradeInfoName;
    }

    public void setGradeInfoName(String gradeInfoName) {
        this.gradeInfoName = gradeInfoName;
    }

    @Override
    public Integer getId() {
        return Integer.valueOf(String.valueOf(super.getId()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getGradeInfoId() {
        return gradeInfoId;
    }

    public void setGradeInfoId(Integer gradeInfoId) {
        this.gradeInfoId = gradeInfoId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public StudentSession(Number userId, String deviceType) {
        super(userId, deviceType);
    }
}
