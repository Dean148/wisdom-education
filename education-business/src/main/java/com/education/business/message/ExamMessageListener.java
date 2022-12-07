package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.SystemConstants;
import com.education.model.entity.MessageLog;
import com.jfinal.json.Jackson;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * 考试消息监听器
 */
@Component
public class ExamMessageListener {

    private final Logger logger = LoggerFactory.getLogger(ExamMessageListener.class);
    private final Jackson jackson = new Jackson();

    @Resource
    private SystemMessageLogService systemMessageLogService;
    @Resource
    private ExamMessageListenerService examMessageListenerService;

    /**
     * 考试提交消息消费
     *  1. 生成考试记录
     *  2. 保存学员答题记录
     *  3. 报错学员错题本
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConfig.EXAM_QUEUE)
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
            if (status != SystemConstants.CONSUME_SUCCESS) {
                examMessageListenerService.doExamCommitMessageBiz(examMessage, messageId);
                channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费
            }
        } catch (Exception e) {
            LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class);
            if (tryCount == SystemConstants.MAX_SEND_COUNT) {
                try {
                    channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费, 消息状态更新为失败状态
                    updateWrapper.set("status", SystemConstants.CONSUME_FAIL);
                } catch (IOException ex) {
                    logger.error("消息状态更新异常....[" + content + "]", e);
                }
            } else {
                try {
                    channel.basicReject(deliveryTag, false);
                } catch (IOException ex) {
                    logger.error("消息重回队列异常......[" + content + "]", e);
                }
            }
            updateWrapper.set("consume_cause", e.getMessage());
            updateWrapper.set("correlation_data_id", messageId);
            systemMessageLogService.update(messageLog, updateWrapper);
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
