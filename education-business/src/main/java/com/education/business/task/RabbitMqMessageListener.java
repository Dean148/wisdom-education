package com.education.business.task;

import com.education.common.utils.ObjectUtils;
import com.education.model.entity.MessageLog;
import com.jfinal.json.Jackson;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.util.Date;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/2 20:25
 */
public class RabbitMqMessageListener implements TaskListener {
    @Override
    public void onMessage(TaskParam taskParam) {

    }

  /*  private final Jackson jackson = new Jackson();

    @Override
    public void onMessage(TaskParam taskParam) {
        MessageLog messageLog = new MessageLog();
        String id = ObjectUtils.generateUuId();
        messageLog.setCorrelationDataId(id);
        messageLog.setCreateDate(new Date());
        messageLog.setContent(jackson.toJson(taskParam.getStr("content")));
        messageLog.setExchange(taskParam.getStr("exchange"));
        messageLog.setRoutingKey(taskParam.getStr("routingKey"));
        // 消息唯一标识
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(ObjectUtils.generateUuId());
    }*/
}
