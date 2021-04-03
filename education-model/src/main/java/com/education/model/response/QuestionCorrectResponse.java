package com.education.model.response;

import com.education.model.entity.StudentInfo;

import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/3 9:27
 */
public class QuestionCorrectResponse {

    private Integer studentMark; // 学员得分
    private Set<StudentInfo> studentInfoSet; // 排行榜


    public Integer getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(Integer studentMark) {
        this.studentMark = studentMark;
    }

    public Set<StudentInfo> getStudentInfoSet() {
        return studentInfoSet;
    }

    public void setStudentInfoSet(Set<StudentInfo> studentInfoSet) {
        this.studentInfoSet = studentInfoSet;
    }
}
