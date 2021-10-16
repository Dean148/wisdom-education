package com.education.business.correct;

import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.List;

/**
 * 试题系统自动批改 (处理客观题及未作答主观题)
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 20:54
 */
public class SystemQuestionCorrect extends QuestionCorrect {

    public SystemQuestionCorrect(StudentQuestionRequest studentQuestionRequest) {
        super(studentQuestionRequest);
    }

    @Override
    void doCorrectStudentQuestion(List<QuestionAnswer> questionInfoList) {

    }
}
