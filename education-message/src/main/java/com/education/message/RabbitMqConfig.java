package com.education.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther: zengjintao
 * @Date: 2019/11/20 14:56
 * @Version:2.1.0
 */
//@Configuration
public class RabbitMqConfig {

    public static final String EXAM_QUEUE = "exam_queue";

    public static final String FANOUT_EXCHANGE = "exam_queue_exchange";

    /**
     * 创建考试消息队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(EXAM_QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
      //  rabbitTemplate.setConfirmCallback(confirmCallback);
      //  rabbitTemplate.setReturnCallback(returnCallback);
        return rabbitTemplate;
    }

}
