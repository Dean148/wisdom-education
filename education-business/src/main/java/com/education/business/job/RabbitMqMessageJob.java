package com.education.business.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.MessageLogMapper;
import com.education.common.component.SpringBeanManager;
import com.education.common.constants.Constants;
import com.education.model.entity.MessageLog;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/2 21:02
 */
public class RabbitMqMessageJob extends BaseJob {

    private static final List<Integer> status = new ArrayList() {
        {
            add(1);
            add(2);
            add(5);
        }
    };

    /**
     * 每隔五分钟扫描一次消息异常记录
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        // 失败次数小于3次并且消费不成功的记录 (减少查询的记录数)
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(MessageLog.class)
                .le(MessageLog::getTryCount, Constants.MAX_SEND_COUNT)
                .in(MessageLog::getStatus, status);
        MessageLogMapper messageLogMapper = SpringBeanManager.getBean(MessageLogMapper.class);
        List<MessageLog> messageLogList = messageLogMapper.selectList(queryWrapper);
        messageLogList.forEach(item -> {
            String content = item.getContent();
            int tryCount = item.getTryCount();

            if (tryCount > Constants.MAX_SEND_COUNT) {

                // 超过三次系统默认消费失败，人工进行处理
                LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                        .set(MessageLog::getStatus, Constants.CONSUME_FAIL)
                        //   .set(MessageLog::getConsumeCause, e.getCause())
                        .eq(MessageLog::getCorrelationDataId, item.getCorrelationDataId());
                messageLogMapper.update(null, updateWrapper);
            }

            else {
                tryCount++;
                LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                        .set(MessageLog::getTryCount, tryCount)
                        .eq(MessageLog::getCorrelationDataId, item.getCorrelationDataId());
                messageLogMapper.update(null, updateWrapper);

                RabbitTemplate rabbitTemplate = SpringBeanManager.getBean(RabbitTemplate.class);
                rabbitTemplate.convertAndSend(item.getExchange(),
                        item.getRoutingKey(),
                        content, new CorrelationData(item.getCorrelationDataId()));
            }
        });
    }
}
