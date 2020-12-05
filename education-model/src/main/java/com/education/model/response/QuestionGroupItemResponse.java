package com.education.model.response;

import com.education.model.dto.ExamQuestionAnswer;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 15:44
 */
public class QuestionGroupItemResponse {

    private String questionTypeName;
    private List<ExamQuestionAnswer> examQuestionAnswerList;

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public List<ExamQuestionAnswer> getExamQuestionAnswerList() {
        return examQuestionAnswerList;
    }

    public void setExamQuestionAnswerList(List<ExamQuestionAnswer> examQuestionAnswerList) {
        this.examQuestionAnswerList = examQuestionAnswerList;
    }
}
