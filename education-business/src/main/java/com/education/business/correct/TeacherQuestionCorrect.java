package com.education.business.correct;

import com.education.common.constants.EnumConstants;
import com.education.common.enums.BooleanEnum;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.Date;
import java.util.Map;

/**
 * 试题管理员批改 (处理已作答主观题)
 * 教师后台批改试题暂时不考虑并发问题
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 21:00
 */
public class TeacherQuestionCorrect extends QuestionCorrect {

    private int teacherMark = 0;

    private volatile boolean initExamInfo = false;

    public TeacherQuestionCorrect(StudentQuestionRequest studentQuestionRequest, ExamInfo examInfo, Map<Integer, String> questionAnswerInfo) {
        super(studentQuestionRequest, examInfo, questionAnswerInfo);
    }

    @Override
    public void correctStudentQuestion() {
        questionAnswerList.forEach(questionAnswerItem -> {
            if (!isObjectiveQuestion(questionAnswerItem.getQuestionType()) &&
                    ObjectUtils.isNotEmpty(questionAnswerItem.getStudentAnswer())) {
                StudentQuestionAnswer studentQuestionAnswer = createStudentQuestionAnswer(questionAnswerItem);
                studentQuestionAnswer.setMark(questionAnswerItem.getStudentMark());
                studentQuestionAnswer.setStudentAnswer(questionAnswerItem.getStudentAnswer());
                teacherMark += questionAnswerItem.getStudentMark();
                // 后台指定加入学员错题本
                if (questionAnswerItem.isErrorQuestionFlag()) {
                    this.addErrorNumber();
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                } else {
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.CORRECTED.getValue());
                }
                super.addObjectiveQuestionAnswerList(studentQuestionAnswer);
            }
        });
    }


    @Override
    public ExamInfo getExamInfo() {
        // 已经设置过ExamInfo 信息
        if (this.initExamInfo) {
            return this.examInfo;
        }
        this.examInfo.setTeacherMark(teacherMark);
        this.examInfo.setCorrectFlag(BooleanEnum.YES.getCode()); // 设置为已批改
        this.examInfo.setUpdateDate(new Date());
        this.examInfo.setErrorNumber(this.examInfo.getErrorNumber() + this.getErrorQuestionNumber());
        if (this.getQuestionNumber() == this.correctQuestionNumber) {
            this.examInfo.setMark(teacherMark);
            this.examInfo.setCorrectType(EnumConstants.CorrectType.TEACHER.getValue());
        } else {
            this.examInfo.setMark(examInfo.getSystemMark() + teacherMark);
            this.examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM_AND_TEACHER.getValue());
        }
        this.sendStudentMessage(); // 发送批改完成消息通知
        this.initExamInfo = true;
        return this.examInfo;
    }
}
