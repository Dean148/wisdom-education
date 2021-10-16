package com.education.business.correct;

import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;

import java.util.List;

/**
 * 试题管理员批改 (处理已作答主观题)
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 21:00
 */
public class TeacherQuestionCorrect extends QuestionCorrect {

    public TeacherQuestionCorrect(StudentQuestionRequest studentQuestionRequest) {
        super(studentQuestionRequest);
    }

    @Override
    void doCorrectStudentQuestion(List<QuestionAnswer> questionInfoList) {

    }
}
