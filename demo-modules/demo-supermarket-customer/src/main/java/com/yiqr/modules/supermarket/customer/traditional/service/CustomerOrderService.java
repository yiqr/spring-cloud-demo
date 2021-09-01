package com.yiqr.modules.supermarket.customer.traditional.service;

import com.yiqr.modules.supermarket.customer.dao.CustomerAccountDao;
import com.yiqr.modules.supermarket.customer.dao.CustomerOrderDao;
import com.yiqr.modules.supermarket.customer.entity.CustomerAccountEntity;
import com.yiqr.modules.supermarket.customer.entity.CustomerOrderEntity;
import com.yiqr.modules.supermarket.customer.entity.QCustomerAccountEntity;
import com.yiqr.modules.supermarket.customer.properties.CustomerProperties;
import com.yiqr.modules.supermarket.customer.traditional.client.IGoodsClient;
import com.yiqr.modules.supermarket.customer.traditional.client.IPayClient;
import com.yiqr.modules.supermarket.customer.traditional.client.mode.GoodsInventoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 3:24 下午
 * @Description:
 */
@Service
@RefreshScope
public class CustomerOrderService {
    @Autowired
    private CustomerProperties customerProperties;
    @Autowired
    private CustomerOrderDao customerOrderDao;
    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private IGoodsClient goodsClient;
    @Autowired
    private IPayClient payClient;


    public CustomerProperties getCustomer() {
        return customerProperties;
    }


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
        //调用支付下单
        String transactionNo = payClient.createOrder(totalPrice);
        //调用商品 减少库存
        goodsClient.reduceInventory(goodsName, goodsNum);
        //保存shopping信息
        CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
        customerOrderEntity.setCustomerName(customerProperties.getName());
        customerOrderEntity.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
        customerOrderEntity.setGoodsName(goodsName);
        customerOrderEntity.setGoodsNum(goodsNum);
        customerOrderEntity.setUnitPrice(goods.getUnitPrice());
        customerOrderEntity.setTotalPrice(totalPrice);
        customerOrderEntity.setTransactionNo(transactionNo);
        customerOrderDao.save(customerOrderEntity);

        if (goodsNum == 11) {
            throw new RuntimeException("人为异常！");
        }

        //修改余额
        customerAccount.setBalanceAmt(customerAccount.getBalanceAmt().subtract(totalPrice));
        customerAccountDao.save(customerAccount);
        return customerOrderEntity;
    }


}
