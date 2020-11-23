package com.education.model.request;


import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:15
 */
public class StudentQuestionRequest {

    private Integer testPaperInfoId;
    private long examTime;
    private List<QuestionAnswer> questionAnswerList;

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }

    public void setExamTime(long examTime) {
        this.examTime = examTime;
    }

    public long getExamTime() {
        return examTime;
    }

    public void setQuestionAnswerList(List<QuestionAnswer> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public List<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }
}
