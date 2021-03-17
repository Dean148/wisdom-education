package com.education.message;

import com.education.model.request.StudentQuestionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 队列消息管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/17 21:05
 */
//@Component
public class QueueManager {

    private static Logger logger = LoggerFactory.getLogger(QueueManager.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送考试提交消息通知
     */
    public void sendExamCommitMessage(StudentQuestionRequest studentQuestionRequest) {

    }
}
