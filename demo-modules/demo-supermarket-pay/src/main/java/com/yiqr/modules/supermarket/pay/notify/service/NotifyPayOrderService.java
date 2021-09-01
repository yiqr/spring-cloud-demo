package com.yiqr.modules.supermarket.pay.notify.service;

import com.yiqr.modules.supermarket.pay.dao.PayOrderDao;
import com.yiqr.modules.supermarket.pay.entity.PayOrderEntity;
import com.yiqr.modules.supermarket.pay.properties.PayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:58 下午
 * @Description:
 */
@Service
public class NotifyPayOrderService {
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
     * @param transactionNo
     * @param transactionAmount
     */
    @Transactional
    public String createOrder(String transactionNo, BigDecimal transactionAmount) {
        PayOrderEntity entity = new PayOrderEntity();
        entity.setTransactionNo(transactionNo);
        entity.setTransactionAmount(transactionAmount);
        payOrderDao.save(entity);
        return transactionNo;
    }
}

