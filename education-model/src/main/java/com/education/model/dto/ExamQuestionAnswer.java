package com.education.model.dto;

import com.education.model.entity.QuestionInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 15:34
 */
public class ExamQuestionAnswer extends QuestionInfo {

    private String studentAnswer;
    private Integer studentMark;

    private int questionMark;
    private Integer correctStatus;


    public void setQuestionMark(int questionMark) {
        this.questionMark = questionMark;
    }

    public int getQuestionMark() {
        return questionMark;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setStudentMark(Integer studentMark) {
        this.studentMark = studentMark;
    }

    public Integer getStudentMark() {
        return studentMark;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }
}
