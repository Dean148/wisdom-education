package com.education.model.dto;

import com.education.model.entity.ExamInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 13:28
 */
public class StudentExamInfoDto extends ExamInfo {

    private Integer subjectId;
    private String subjectName;
    private String testPaperInfoName;
    private Integer questionNumber;
    private String startTime;
    private String endTime;
    private Integer testPaperExamTime; // 试卷考试时间

    public void setTestPaperExamTime(Integer testPaperExamTime) {
        this.testPaperExamTime = testPaperExamTime;
    }

    public Integer getTestPaperExamTime() {
        return testPaperExamTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTestPaperInfoName() {
        return testPaperInfoName;
    }

    public void setTestPaperInfoName(String testPaperInfoName) {
        this.testPaperInfoName = testPaperInfoName;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
