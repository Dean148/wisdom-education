package com.education.business.parser;

import com.education.model.entity.ExamInfo;
import com.education.model.request.StudentQuestionRequest;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/17 20:44
 */
public abstract class QuestionCorrect {

    private StudentQuestionRequest studentQuestionRequest;
    private ExamInfo examInfo;

    private int rightQuestionNumber; // 答对题数
    private int errorQuestionNumber; // 答错题数
    private int questionNumber; // 试题总数

    public QuestionCorrect(StudentQuestionRequest studentQuestionRequest) {
        this.questionNumber = studentQuestionRequest.getQuestionAnswerList().size();
    }

    public void correctStudentQuestion() {
        doCorrectStudentQuestion();
    }

    abstract void doCorrectStudentQuestion();
}
