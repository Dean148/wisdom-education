package com.education.business.correct;

import com.education.common.constants.EnumConstants;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.Date;
import java.util.List;

/**
 * 试题管理员批改 (处理已作答主观题)
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 21:00
 */
public class TeacherQuestionCorrect extends QuestionCorrect {

    private int teacherMark = 0;

    public TeacherQuestionCorrect(StudentQuestionRequest studentQuestionRequest, ExamInfo examInfo) {
        super(studentQuestionRequest, examInfo);
    }

    @Override
    public void correctStudentQuestion(List<QuestionAnswer> questionAnswerList) {
        questionAnswerList.forEach(questionAnswerItem -> {
            if (!isObjectiveQuestion(questionAnswerItem.getQuestionType()) &&
                    ObjectUtils.isNotEmpty(questionAnswerItem.getStudentAnswer())) {
                StudentQuestionAnswer studentQuestionAnswer = createStudentQuestionAnswer(questionAnswerItem);
                studentQuestionAnswer.setMark(questionAnswerItem.getStudentMark());
                teacherMark += questionAnswerItem.getStudentMark();
                // 后台指定加入学员错题本
                if (questionAnswerItem.isErrorQuestionFlag()) {
                    this.addErrorNumber();
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                } else {
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.CORRECTED.getValue());
                }
            }
        });
    }


    @Override
    public ExamInfo getExamInfo() {
        this.examInfo.setTeacherMark(teacherMark);
        this.examInfo.setCorrectFlag(EnumConstants.Flag.YES.getValue()); // 设置为已批改
        this.examInfo.setUpdateDate(new Date());
        this.examInfo.setErrorNumber(this.examInfo.getErrorNumber() + this.getErrorQuestionNumber());
        if (this.getQuestionNumber() == this.correctQuestionNumber) {
            this.examInfo.setMark(teacherMark);
            this.examInfo.setCorrectType(EnumConstants.CorrectType.TEACHER.getValue());
        } else {
            this.examInfo.setMark(examInfo.getSystemMark() + teacherMark);
            int subjectiveQuestionNumber = examInfo.getSubjectiveQuestionNumber()
                    + this.subjectiveQuestionNumber;
            this.examInfo.setSubjectiveQuestionNumber(subjectiveQuestionNumber);
            this.examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM_AND_TEACHER.getValue());
        }
        this.sendStudentMessage(); // 发送消息通知
        return this.examInfo;
    }
}
