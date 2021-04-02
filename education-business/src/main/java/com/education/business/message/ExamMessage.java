package com.education.business.message;

import com.education.model.entity.StudentWrongBook;
import com.education.model.request.QuestionAnswer;

import java.io.Serializable;
import java.util.List;

public class ExamMessage implements Serializable {

    /**
     * 错题本列表
     */
    private List<StudentWrongBook> studentWrongBookList;

    /**
     * 答题记录列表
     */
    private List<QuestionAnswer> questionAnswerList;


    public List<StudentWrongBook> getStudentWrongBookList() {
        return studentWrongBookList;
    }

    public void setStudentWrongBookList(List<StudentWrongBook> studentWrongBookList) {
        this.studentWrongBookList = studentWrongBookList;
    }

    public List<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<QuestionAnswer> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }
}
