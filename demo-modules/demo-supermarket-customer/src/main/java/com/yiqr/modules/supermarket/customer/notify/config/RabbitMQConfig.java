package com.yiqr.modules.supermarket.customer.notify.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 3:21 下午
 * @Description:
 */
@Configuration
public class RabbitMQConfig {


    @Bean
    public Queue createOrderQueue() {
        return new Queue("create-order-goods.queue", true, false, false);
    }

    @Bean
    public TopicExchange createOrderTopicExchange() {
        return new TopicExchange("create-order-goods-topic-exchange", true, false);
    }

    /**
     * 创建绑定
     *
     * @return
     */
    @Bean
    public Binding bindingCreateOrder() {
        return BindingBuilder.bind(createOrderQueue()).to(createOrderTopicExchange()).with("create-order-goods.queue");
    }


    @Bean
    public Queue orderPayQueue() {
        return new Queue("order-pay.queue", true, false, false);
    }

    @Bean
    public TopicExchange orderPayTopicExchange() {
        return new TopicExchange("order-pay-topic-exchange", true, false);
    }

    @Bean
    public Binding bindingOrderPay() {
        return BindingBuilder.bind(orderPayQueue()).to(orderPayTopicExchange()).with("order-pay.queue");
    }


    //// 创建死信交换机
    //@Bean
    //public FanoutExchange deadExchange() {
    //    return new FanoutExchange("dead_order_fanout_exchange", true, false);
    //}
    //
    //// 死信队列
    //@Bean
    //public Queue deadOrderQueue() {
    //    return new Queue("dead.order.queue", true);
    //}
    //
    //// 私信交换机绑定死信队列
    //@Bean
    //public Binding bindDeadOrder() {
    //    return BindingBuilder.bind(deadOrderQueue()).to(deadExchange());
    //}
    //
    //
    //@Bean
    //public FanoutExchange fanoutExchange() {
    //    return new FanoutExchange("order_fanout_exchange", true, false);
    //}
    //
    //// 队列绑定私信交换机
    //@Bean
    //public Queue orderQueue() {
    //    Map<String, Object> args = new HashMap<>();
    //    args.put("x-dead-letter-exchange", "dead_order_fanout_exchange");
    //    return new Queue("order.queue", true, false, false, args);
    //}
    //
    //@Bean
    //public Binding bindorder() {
    //    return BindingBuilder.bind(orderQueue()).to(fanoutExchange());
    //}

    /**
     * 消息确认回调函数
     * <p>
     * 推送消息存在四种情况：
     * 1.消息推送到sever，交换机和队列啥都没找到
     * 2.消息推送到server，但是在server里找不到交换机
     * 3.消息推送到server，找到交换机了，但是没找到队列
     * 4.消息推送成功
     * <p>
     * 情况 1/2/4 会进入 confirm 回调函数
     * 情况3两个回调函数都会进入
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
                System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
                System.out.println("ConfirmCallback:     " + "原因：" + cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     " + "消息：" + message);
                System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
                System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
                System.out.println("ReturnCallback:     " + "交换机：" + exchange);
                System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
            }
        });

        return rabbitTemplate;
    }


}
