package com.education.model.request;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:47
 */
public class QuestionAnswer {

    private Integer questionInfoId;
    private Integer questionMark;
    private String answer;
    private String studentAnswer;
    private Integer questionType;
    private String comment; // 评语
    private Integer correctStatus; // 批改状态
    private boolean errorQuestionFlag; // 是否加入错题本

    public boolean isErrorQuestionFlag() {
        return errorQuestionFlag;
    }

    public void setErrorQuestionFlag(boolean errorQuestionFlag) {
        this.errorQuestionFlag = errorQuestionFlag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }

    public Integer getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(Integer questionMark) {
        this.questionMark = questionMark;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
