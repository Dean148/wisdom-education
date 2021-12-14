package com.education.business.message;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMq 配置
 * @Auther: zengjintao
 * @Date: 2019/11/20 14:56
 * @Version: 1.0.3
 */
@Configuration
public class RabbitMqConfig {

    public static final String EXAM_QUEUE = "exam_queue";
    public final static String EXAM_QUEUE_ROUTING_KEY = "exam_queue_routing_key";
    public final static String EXAM_DIRECT_EXCHANGE = "exam_queue_exchange";

    @Autowired
    private RabbitTemplate.ConfirmCallback confirmCallback;
    @Autowired
    private RabbitTemplate.ReturnCallback returnCallback;

    /**
     * 创建考试消息队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(EXAM_QUEUE);
    }


    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder
                .directExchange(EXAM_DIRECT_EXCHANGE)
                .build();
    }

    @Bean
    public Binding bindingDeadExchange(DirectExchange directExchange) {
        return BindingBuilder.bind(queue()).to(directExchange).with(EXAM_QUEUE_ROUTING_KEY);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory RabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleRabbitListenerContainerFactory;
    }
}
