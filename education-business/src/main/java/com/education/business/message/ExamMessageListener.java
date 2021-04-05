package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.StudentQuestionAnswerService;
import com.education.business.service.education.StudentWrongBookService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.Constants;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.MessageLog;
import com.education.model.entity.StudentQuestionAnswer;
import com.jfinal.json.Jackson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 考试消息监听器
 */
@Component
@Slf4j
public class ExamMessageListener {

    private final Jackson jackson = new Jackson();

    @Autowired
    private StudentWrongBookService studentWrongBookService;
    @Autowired
    private SystemMessageLogService systemMessageLogService;
    @Autowired
    private StudentQuestionAnswerService studentQuestionAnswerService;
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private TestPaperInfoService testPaperInfoService;

    /**
     * 考试提交消息消费
     *  1. 生成考试记录
     *  2. 保存学员答题记录
     *  3. 报错学员错题本
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConfig.EXAM_QUEUE)
    // @Transactional
    public void onExamCommitMessage(Message message, Channel channel) {
        String content = new String(message.getBody());
        ExamMessage examMessage = jackson.parse(content, ExamMessage.class);
        String messageId = examMessage.getMessageId();
        MessageLog messageLog = systemMessageLogService.selectByMessageId(examMessage.getMessageId());
        if (messageLog == null) {
            return;
        }
        Integer status = messageLog.getStatus();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        int tryCount = messageLog.getTryCount();
        try {
            if (status != Constants.CONSUME_SUCCESS) {
                // 保存考试记录
                ExamInfo examInfo = examMessage.getExamInfo();
                examInfo.setCreateDate(new Date());
                examInfoService.save(examInfo);
                // 批量保存学员错题
                if (examMessage.getStudentWrongBookList() != null) {
                    studentWrongBookService.saveBatch(examMessage.getStudentWrongBookList());
                }
                // 保存学员答题记录
                List<StudentQuestionAnswer> studentQuestionAnswerList = examMessage.getStudentQuestionAnswerList();
                studentQuestionAnswerList.stream().forEach(item -> item.setExamInfoId(examInfo.getId()));

                studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);

                LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                        .set(MessageLog::getStatus, Constants.CONSUME_SUCCESS)
                        .eq(MessageLog::getCorrelationDataId, messageId);
                systemMessageLogService.update(null, updateWrapper);
                // 更新试卷参考人数
                testPaperInfoService.updateExamNumber(examInfo.getTestPaperInfoId());
                channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费
            }
        } catch (Exception e) {
            if (tryCount == Constants.MAX_SEND_COUNT) {
                try {
                    channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费, 消息状态更新为失败状态
                    LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                            .set(MessageLog::getStatus, Constants.CONSUME_FAIL)
                            .set(MessageLog::getConsumeCause, e.getCause())
                            .eq(MessageLog::getCorrelationDataId, messageId);
                    systemMessageLogService.update(messageLog, updateWrapper);
                } catch (IOException ex) {
                    log.error("消息状态更新异常....[" + content + "]", e);
                }
            } else {
                try {
                    channel.basicReject(deliveryTag, false);
                } catch (IOException ex) {
                    log.error("消息重回队列异常......[" + content + "]", e);
                }
            }
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
