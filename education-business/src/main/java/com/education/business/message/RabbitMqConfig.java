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
 * @Version:2.1.0
 */
@Configuration
public class RabbitMqConfig {

    public static final String EXAM_QUEUE = "exam_queue";
    public static final String FANOUT_EXCHANGE = "exam_queue_exchange";
    public final static String EXAM_QUEUE_ROUTING_KEY = "exam_queue_routing_key";

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

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

/*    public DirectExchange directExchange() {
        return new DirectExchange(FANOUT_EXCHANGE);
    }*/

    /**
     * 将EXAM_QUEUE 绑定到交换机
     * @return
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

 /*   @Bean
    public Binding fanoutBinding1() {
       // return BindingBuilder.bind(queue())
        return BindingBuilder.bind(queue()).to(directExchange()).with("/a");
       // return BindingBuilder.bind(queue()).to(directExchange());
    }*/

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return rabbitListenerContainerFactory;
    }
}
