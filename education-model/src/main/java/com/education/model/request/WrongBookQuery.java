package com.education.model.request;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/5 14:41
 */
public class WrongBookQuery {

    private Integer subjectId;
    private Integer questionType;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
}
