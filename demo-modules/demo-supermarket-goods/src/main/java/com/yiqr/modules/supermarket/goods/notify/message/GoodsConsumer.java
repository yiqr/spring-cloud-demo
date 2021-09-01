package com.yiqr.modules.supermarket.goods.notify.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.yiqr.modules.supermarket.goods.notify.client.MessageClient;
import com.yiqr.modules.supermarket.goods.notify.service.NotifyGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 6:54 下午
 * @Description:
 */
@Slf4j
@Component
public class GoodsConsumer {
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private NotifyGoodsService notifyGoodsService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "create-order-goods.queue", durable = "true"),
            exchange = @Exchange(name = "create-order-goods-topic-exchange", durable = "true", type = "topic"),
            key = "create-order-goods.queue"
    ))
    @RabbitHandler
    public void onPayOrderMessage(@Payload String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        CustomerMessage customerMessage = JSON.parseObject(message, CustomerMessage.class);
        try {
            JSONObject messageBody = JSON.parseObject(customerMessage.getMessageBody(), JSONObject.class);
            notifyGoodsService.reduceInventory(messageBody.getString("goodsName"), messageBody.getInteger("num"));
            log.info("消息处理成功");
            //消息处理
            messageClient.messageHandlerSuccess(customerMessage.getId());
            // ACK（手工签收）
            Long delveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(delveryTag, false);
        } catch (Exception ex) {
            log.info("消费异常：{}", ex);
        }
    }
}
