package com.yiqr.modules.supermarket.pay.notify.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.yiqr.modules.supermarket.pay.notify.client.MessageClient;
import com.yiqr.modules.supermarket.pay.notify.service.NotifyPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 5:39 下午
 * @Description:
 */
@Slf4j
@Component
public class PayConsumer {

    @Autowired
    private MessageClient messageClient;
    @Autowired
    private NotifyPayOrderService notifyPayOrderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-pay.queue", durable = "true"),
            exchange = @Exchange(name = "order-pay-topic-exchange", durable = "true", type = "topic"),
            key = "order-pay.queue"
    ))
    @RabbitHandler
    public void onPayOrderMessage(@Payload String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        try {
            CustomerMessage customerMessage = JSON.parseObject(message, CustomerMessage.class);
            JSONObject messageBody = JSON.parseObject(customerMessage.getMessageBody(), JSONObject.class);
            notifyPayOrderService.createOrder(messageBody.getString("transactionNo"), new BigDecimal(messageBody.getString("totalPrice")));
            log.info("消息处理成功");
            //消息处理
            messageClient.messageHandlerSuccess(customerMessage.getId());
            // ACK（手工签收）
            Long delveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(delveryTag, false);
        } catch (Exception ex) {
            log.info("消费异常：{}", ex);
            Long delveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(delveryTag, false);
        }
    }

}
