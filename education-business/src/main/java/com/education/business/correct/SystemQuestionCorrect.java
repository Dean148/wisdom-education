package com.education.business.correct;

import com.education.business.message.ExamMessage;
import com.education.business.message.QueueManager;
import com.education.common.constants.EnumConstants;
import com.education.common.utils.DateUtils;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;

import java.util.Date;
import java.util.List;

/**
 * 试题系统自动批改 (处理客观题及未作答主观题)
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 20:54
 */
public class SystemQuestionCorrect extends QuestionCorrect {

    private int systemMark = 0;// 系统判分

    private QueueManager queueManager;


    public SystemQuestionCorrect(StudentQuestionRequest studentQuestionRequest, ExamInfo examInfo, QueueManager queueManager) {
        super(studentQuestionRequest, examInfo);
        this.queueManager = queueManager;
    }

    /**
     * 批改客观题及答案为空的主观题
     * @param
     */
    @Override
    public void correctStudentQuestion() {
        for (QuestionAnswer questionAnswerItem : questionAnswerList) {
            if (isObjectiveQuestion(questionAnswerItem.getQuestionType()) ||
                    ObjectUtils.isEmpty(questionAnswerItem.getStudentAnswer())) {
                this.correctQuestionNumber++; // 批改试题数量加1
                StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
                studentQuestionAnswer.setQuestionInfoId(questionAnswerItem.getQuestionInfoId());
                studentQuestionAnswer.setStudentId(null);
                studentQuestionAnswer.setQuestionPoints(questionAnswerItem.getQuestionMark());

                // 客观题答案不为空
                if (ObjectUtils.isNotEmpty(questionAnswerItem.getStudentAnswer())) {
                    this.objectiveQuestionNumber++; // 客观题加1
                    String questionAnswer = questionAnswerItem.getAnswer().replaceAll(",", "");
                    String studentAnswer = questionAnswerItem.getStudentAnswer();
                    String studentAnswerProxy = null;
                    String questionAnswerProxy = ObjectUtils.charSort(questionAnswer);
                    if (ObjectUtils.isNotEmpty(studentAnswer)) {
                        studentAnswerProxy = ObjectUtils.charSort(studentAnswer.replaceAll(",", ""));
                    }

                    if (questionAnswerProxy.equals(studentAnswerProxy)) {
                        studentQuestionAnswer.setMark(questionAnswerItem.getQuestionMark());
                        this.systemMark += questionAnswerItem.getQuestionMark();
                        super.addRightNumber();
                        studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.RIGHT.getValue());
                    } else {
                        this.newStudentWrongBook(questionAnswerItem);
                        studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                    }
                } else {
                    // 未作答的主观题
                    this.newStudentWrongBook(questionAnswerItem);
                    this.subjectiveQuestionNumber++; // 主观题加1
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                }
            }
        }

        // rabbitmq 发送考试记录消息
        ExamMessage examMessage = new ExamMessage();
        examMessage.setQuestionAnswerList(questionAnswerList);
        examMessage.setStudentWrongBookList(getStudentWrongBookList());
        queueManager.sendExamCommitMessage(examMessage);
    }


    @Override
    public ExamInfo getExamInfo() {
        this.examInfo.setCreateDate(new Date());
        this.examInfo.setSystemMark(systemMark);
        this.examInfo.setRightNumber(super.getRightQuestionNumber());
        this.examInfo.setErrorNumber(super.getErrorQuestionNumber());
        this.examInfo.setSubjectiveQuestionNumber(subjectiveQuestionNumber);
        this.examInfo.setQuestionNumber(super.getQuestionNumber());
        long examTime = super.getExamTime();
        examInfo.setExamTime(DateUtils.getDate(examTime));
        examInfo.setExamTimeLongValue(examTime);

        // 系统批改试题数量等于试卷总题数
        if (this.correctQuestionNumber == this.getQuestionNumber()) {
            this.examInfo.setCorrectFlag(EnumConstants.Flag.YES.getValue());
            this.examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM.getValue());
            this.sendStudentMessage();
        }
        return this.examInfo;
    }
}
