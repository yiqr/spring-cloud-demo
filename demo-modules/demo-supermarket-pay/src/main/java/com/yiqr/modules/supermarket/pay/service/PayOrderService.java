package com.yiqr.modules.supermarket.pay.service;

import com.yiqr.modules.supermarket.pay.dao.PayOrderDao;
import com.yiqr.modules.supermarket.pay.entity.PayOrderEntity;
import com.yiqr.modules.supermarket.pay.properties.PayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:58 下午
 * @Description:
 */
@Service
public class PayOrderService {
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private PayProperties payProperties;


    public PayProperties getConfig() {
        return payProperties;
    }

    /**
     * 创建交易订单
     *
     * @param transactionAmount
     */
    public String createOrder(BigDecimal transactionAmount) {
        String transactionNo = UUID.randomUUID().toString().replaceAll("-", "");
        PayOrderEntity entity = new PayOrderEntity();
        entity.setTransactionNo(transactionNo);
        entity.setTransactionAmount(transactionAmount);
        payOrderDao.save(entity);
        return transactionNo;
    }
}

