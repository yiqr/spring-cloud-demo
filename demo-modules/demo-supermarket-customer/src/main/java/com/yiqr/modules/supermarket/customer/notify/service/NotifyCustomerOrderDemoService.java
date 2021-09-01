package com.yiqr.modules.supermarket.customer.notify.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiqr.modules.supermarket.customer.dao.CustomerAccountDao;
import com.yiqr.modules.supermarket.customer.dao.CustomerOrderDao;
import com.yiqr.modules.supermarket.customer.entity.CustomerAccountEntity;
import com.yiqr.modules.supermarket.customer.entity.CustomerOrderEntity;
import com.yiqr.modules.supermarket.customer.entity.QCustomerAccountEntity;
import com.yiqr.modules.supermarket.customer.traditional.client.IGoodsClient;
import com.yiqr.modules.supermarket.customer.traditional.client.mode.GoodsInventoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 2:25 下午
 * @Description:
 */
@Service
public class NotifyCustomerOrderDemoService {
    @Autowired
    CustomerOrderDao customerOrderDao;
    @Autowired
    CustomerAccountDao customerAccountDao;
    @Autowired
    NotifyCustomerMessageService notifyCustomerMessageService;

    @Autowired
    private IGoodsClient goodsClient;


    @Transactional
    public CustomerOrderEntity shopping(String customerName, Integer goodsNum, String goodsName) {
        CustomerAccountEntity customerAccount = null;
        Optional<CustomerAccountEntity> customerAccountOptional = customerAccountDao.findOne(QCustomerAccountEntity.customerAccountEntity.customerName.eq(customerName));
        if (!customerAccountOptional.isPresent()) {
            throw new RuntimeException("账户信息不存在");
        }
        customerAccount = customerAccountOptional.get();
        //调用商品库存盘点商品是否满足条件
        GoodsInventoryEntity goods = goodsClient.getGoods(goodsName);
        if (Objects.isNull(goods) || Objects.isNull(goods.getGoodsNum()) || goodsNum > goods.getGoodsNum()) {
            throw new RuntimeException("库存不足");
        }
        //计算总额
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal unitPrice = goods.getUnitPrice();
        if (Objects.isNull(unitPrice) || BigDecimal.ZERO.compareTo(unitPrice) >= 0) {
            unitPrice = BigDecimal.ZERO;
        }
        totalPrice = new BigDecimal(goodsNum).multiply(unitPrice);
        if (customerAccount.getBalanceAmt().compareTo(totalPrice) < 0) {
            throw new RuntimeException("余额不足");
        }

        //调用MQ服务发送消息  --  支付订单
        String txNo = UUID.randomUUID().toString().replaceAll("-", "");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionNo", txNo);
        jsonObject.put("totalPrice", totalPrice);
        String msgId = notifyCustomerMessageService.addWaitSendMessage(txNo, "order-pay.queue", JSON.toJSONString(jsonObject));


        //调用MQ服务发送消息  --  商品出库
        JSONObject goodsJson = new JSONObject();
        goodsJson.put("goodsName", goodsName);
        goodsJson.put("num", goodsNum);
        String goodsMsgId = notifyCustomerMessageService.addWaitSendMessage(txNo, "create-order-goods.queue", JSON.toJSONString(goodsJson));


        //保存shopping信息
        CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
        customerOrderEntity.setCustomerName(customerAccount.getCustomerName());
        customerOrderEntity.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
        customerOrderEntity.setGoodsName(goodsName);
        customerOrderEntity.setGoodsNum(goodsNum);
        customerOrderEntity.setUnitPrice(goods.getUnitPrice());
        customerOrderEntity.setTotalPrice(totalPrice);
        customerOrderEntity.setTransactionNo(txNo);
        customerOrderDao.save(customerOrderEntity);

        //修改余额
        customerAccount.setBalanceAmt(customerAccount.getBalanceAmt().subtract(totalPrice));
        customerAccountDao.save(customerAccount);

        notifyCustomerMessageService.confirmAndSendMessage("order-pay-topic-exchange", msgId);
        notifyCustomerMessageService.confirmAndSendMessage("create-order-goods-topic-exchange", goodsMsgId);
        return customerOrderEntity;
    }


}
