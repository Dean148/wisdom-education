package com.education.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息唯一标识: {}", correlationData.getId());
        log.info("确认状态: {}", ack);
        String messageId = correlationData.getId();
    }
}
