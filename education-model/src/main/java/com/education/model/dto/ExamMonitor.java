package com.education.model.dto;

import com.education.model.entity.StudentInfo;

import java.io.Serializable;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/29 16:26
 */
public class ExamMonitor implements Serializable {

    private int answerQuestionCount = 0; // 已答题
    private int questionCount;
    private StudentInfo studentInfo;
    private String device; // 答题设备
    private String startExamTime; // 考试开始时间


    private String rateStr; // 答题进度

    public int getAnswerQuestionCount() {
        return answerQuestionCount;
    }

    public String getRateStr() {
        return answerQuestionCount + "/" + questionCount;
    }

    public void setAnswerQuestionCount(int answerQuestionCount) {
        this.answerQuestionCount = answerQuestionCount;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getStartExamTime() {
        return startExamTime;
    }

    public void setStartExamTime(String startExamTime) {
        this.startExamTime = startExamTime;
    }
}
