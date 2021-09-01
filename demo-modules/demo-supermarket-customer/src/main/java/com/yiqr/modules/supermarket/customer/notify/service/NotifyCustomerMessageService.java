package com.yiqr.modules.supermarket.customer.notify.service;

import com.alibaba.fastjson.JSON;
import com.yiqr.modules.supermarket.customer.dao.CustomerMessageDao;
import com.yiqr.modules.supermarket.customer.entity.CustomerMessageEntity;
import com.yiqr.modules.supermarket.customer.entity.QCustomerMessageEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 4:03 下午
 * @Description:
 */
@Service
public class NotifyCustomerMessageService {

    @Autowired
    private CustomerMessageDao customerMessageDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 消息预发送
     *
     * @param txNo
     * @param consumerQueue
     * @param messageBody
     * @return
     */
    public String addWaitSendMessage(String txNo, String consumerQueue, String messageBody) {
        if (!StringUtils.isNotBlank(txNo)) {
            throw new RuntimeException("事务编号不能为空");
        }
        if (!StringUtils.isNotBlank(consumerQueue)) {
            throw new RuntimeException("消费队列不能为空");
        }
        if (!StringUtils.isNotBlank(messageBody)) {
            throw new RuntimeException("消息内容不能为空");
        }
        long count = customerMessageDao.count(
                QCustomerMessageEntity.customerMessageEntity.txNo
                        .eq(txNo).and(QCustomerMessageEntity.customerMessageEntity.consumerQueue.eq(consumerQueue)));
        if (count > 0) {
            throw new RuntimeException("事务幂等性");
        }
        // 保存消息内容到数据库
        CustomerMessageEntity message = new CustomerMessageEntity();
        message.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        message.setTxNo(txNo);
        message.setConsumerQueue(consumerQueue);
        message.setMessageBody(messageBody);
        message.setResendTimes(0);  // 重发次数
        message.setStatus(0);       // 消息状态（0:未发送 1:已发送）
        message.setConfirmTime(null);
        message.setCreateTime(new Date());
        customerMessageDao.save(message);
        return message.getId();
    }

    /**
     * 发送消息
     *
     * @param exchange
     * @param messageId
     */
    public void confirmAndSendMessage(String exchange, String messageId) {
        if (StringUtils.isEmpty(messageId)) {
            throw new RuntimeException("消息id不能为空");
        }
        // 获取消息
        CustomerMessageEntity message = null;
        Optional<CustomerMessageEntity> messageOptional = customerMessageDao.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new RuntimeException("未找到消息记录，请检查参数是否正确");
        }
        message = messageOptional.get();
        // 更新消息状态为发送中
        message.setStatus(1);
        // 更新发送时间
        message.setConfirmTime(new Date());
        customerMessageDao.save(message);
        // 向MQ发送消息
        rabbitTemplate.convertAndSend(exchange, message.getConsumerQueue(), JSON.toJSONString(message));
    }

    /**
     * 消息处理成功
     *
     * @param messageId
     */
    public void messageHandlerSuccess( String messageId) {
        CustomerMessageEntity message = null;
        Optional<CustomerMessageEntity> messageOptional = customerMessageDao.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new RuntimeException("未找到消息记录，请检查参数是否正确");
        }
        // 更新消息状态为业务处理成功
        message = messageOptional.get();
        message.setStatus(2);
        message.setUpdateTime(new Date());
        customerMessageDao.save(message);
    }

    /**
     * 消息处理失败
     *
     * @param messageId
     */
    public void messageHandlerFail(String messageId) {
        CustomerMessageEntity message = null;
        Optional<CustomerMessageEntity> messageOptional = customerMessageDao.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new RuntimeException("未找到消息记录，请检查参数是否正确");
        }
        // 更新消息状态为业务处理失败
        message = messageOptional.get();
        message.setStatus(3);
        message.setUpdateTime(new Date());
        customerMessageDao.save(message);
    }

}
