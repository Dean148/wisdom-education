package com.education.model.dto;

import com.education.model.entity.CourseInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 15:01
 */
public class CourseInfoDto extends CourseInfo {

    private String subjectName;
    private String gradeInfoName;

    private Integer studentId;
    private Integer collectFlag;

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getGradeInfoName() {
        return gradeInfoName;
    }

    public void setGradeInfoName(String gradeInfoName) {
        this.gradeInfoName = gradeInfoName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
