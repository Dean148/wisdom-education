package com.education.business.correct;

import cn.hutool.core.util.StrUtil;
import com.education.business.message.ExamMessage;
import com.education.business.message.QueueManager;
import com.education.business.message.RabbitMqConfig;
import com.education.common.constants.EnumConstants;
import com.education.common.enums.BooleanEnum;
import com.education.common.utils.DateUtils;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.Date;
import java.util.Map;

/**
 * 试题系统自动批改 (处理客观题及未作答主观题, 然后保存学员本次考试答题记录)
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 20:54
 */
public class SystemQuestionCorrect extends QuestionCorrect {

    private int systemMark = 0;// 系统判分

    private QueueManager queueManager;

    private Integer commitAfterType;

    public SystemQuestionCorrect(StudentQuestionRequest studentQuestionRequest,
                                 QueueManager queueManager,
                                 Map<Integer, String> questionAnswerInfo, Integer commitAfterType) {
        super(studentQuestionRequest, null, questionAnswerInfo);
        this.queueManager = queueManager;
        this.commitAfterType = commitAfterType;
    }



    /**
     * 批改客观题及答案为空的主观题
     * @param
     */
    @Override
    public void correctStudentQuestion() {
        for (QuestionAnswer questionAnswerItem : questionAnswerList) {
            StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
            studentQuestionAnswer.setQuestionInfoId(questionAnswerItem.getQuestionInfoId());
            studentQuestionAnswer.setStudentId(this.getStudentId());
            studentQuestionAnswer.setQuestionPoints(questionAnswerItem.getQuestionMark());
            studentQuestionAnswer.setStudentAnswer(questionAnswerItem.getStudentAnswer());
            studentQuestionAnswer.setCreateDate(new Date());
            if (isObjectiveQuestion(questionAnswerItem.getQuestionType()) ||
                    ObjectUtils.isEmpty(questionAnswerItem.getStudentAnswer())) {
                this.correctQuestionNumber++; // 批改试题数量加1
                this.objectiveQuestionNumber++; // 客观题加1

                String questionAnswer = questionAnswerInfo.get(questionAnswerItem.getQuestionInfoId());
                // 客观题答案不为空
                if (ObjectUtils.isNotEmpty(questionAnswerItem.getStudentAnswer())) {
                    questionAnswer = questionAnswer.replaceAll(StrUtil.COMMA, "");
                    String studentAnswer = questionAnswerItem.getStudentAnswer();
                    String studentAnswerProxy = null;
                    String questionAnswerProxy = ObjectUtils.charSort(questionAnswer);
                    if (ObjectUtils.isNotEmpty(studentAnswer)) {
                        studentAnswerProxy = ObjectUtils.charSort(studentAnswer.replaceAll(StrUtil.COMMA, ""));
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
                    // 未作答的客观题
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                }
            } else {
                if (ObjectUtils.isEmpty(questionAnswerItem.getStudentAnswer())) {
                    // 未作答的主观题系统直接判为错题
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                } else {
                    // 已作答的主观题设置为待批改状态
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.CORRECT_RUNNING.getValue());
                }
                // 主观题数量加1
                this.subjectiveQuestionNumber++;
                // 缓存主观题信息
                super.addObjectiveQuestionAnswerList(studentQuestionAnswer);
            }
            studentQuestionAnswerList.add(studentQuestionAnswer);
        }

        // rabbitmq 发送考试提交消息
        if (EnumConstants.CommitAfterType.SHOW_MARK_AFTER_CORRECT.getValue().equals(this.commitAfterType)) {
            ExamMessage examMessage = new ExamMessage();
            examMessage.setStudentQuestionAnswerList(studentQuestionAnswerList);
            examMessage.setStudentWrongBookList(getStudentWrongBookList());
            examMessage.setExamInfo(this.getExamInfo());
            examMessage.setRoutingKey(RabbitMqConfig.EXAM_QUEUE_ROUTING_KEY);
            examMessage.setExchange(RabbitMqConfig.EXAM_DIRECT_EXCHANGE);
            queueManager.sendQueueMessage(examMessage);
        }
    }


    @Override
    public ExamInfo getExamInfo() {
        if (this.examInfo != null) {
            return examInfo;
        }
        this.examInfo = new ExamInfo();
        this.examInfo.setCreateDate(new Date());
        this.examInfo.setSystemMark(systemMark);
        this.examInfo.setRightNumber(super.getRightQuestionNumber());
        this.examInfo.setErrorNumber(super.getErrorQuestionNumber());
        this.examInfo.setSubjectiveQuestionNumber(subjectiveQuestionNumber);
        this.examInfo.setQuestionNumber(super.getQuestionNumber());
        long examTime = super.getExamTime();
        examInfo.setStudentId(getStudentId());
        examInfo.setExamTime(DateUtils.secondToHourMinute(examTime));
        examInfo.setExamTimeLongValue(examTime);
        examInfo.setTestPaperInfoId(super.getTestPaperInfoId());

        // 系统批改试题数量等于试卷总题数
        if (this.correctQuestionNumber == this.getQuestionNumber()) {
            this.examInfo.setCorrectFlag(BooleanEnum.YES.getCode());
            this.examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM.getValue());
            this.examInfo.setMark(systemMark);
            this.sendStudentMessage();
        }
        return this.examInfo;
    }
}
