package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.Constants;
import com.education.model.entity.MessageLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Name: 消息确认发送到交换机回调
 * @Auther: 66
 * @Date: 2019/11/20 15:49
 * @Version:2.1.0
 */
@Component("confirmCallback")
@Slf4j
public class ConfirmCallbackImpl implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private SystemMessageLogService systemMessageLogService;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息唯一标识: {}", correlationData.getId());
        log.info("确认状态: {}", ack);

        String messageId = correlationData.getId();
        LambdaUpdateWrapper updateWrapper = null;
        if (ack) {
          /*  updateWrapper = new LambdaUpdateWrapper<MessageLog>()
                    .set(MessageLog::getStatus,  Constants.SEND_SUCCESS)
                    .eq(MessageLog::getCorrelationDataId, messageId);*/
        } else {

            // 消息发送失败
            log.error("造成原因: {}", cause);
          /*  updateWrapper = new LambdaUpdateWrapper<MessageLog>()
                    .set(MessageLog::getStatus, Constants.SEND_FAIL)
                    .set(MessageLog::getFailCause, cause)
                    .eq(MessageLog::getCorrelationDataId, messageId);*/
        }
      //  systemMessageLogService.update(null, updateWrapper);
    }
}
