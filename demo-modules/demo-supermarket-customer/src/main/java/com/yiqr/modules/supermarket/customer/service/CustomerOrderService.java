package com.yiqr.modules.supermarket.customer.service;

import com.yiqr.modules.supermarket.customer.dao.CustomerOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 3:24 下午
 * @Description:
 */
@Service
public class CustomerOrderService {
    @Autowired
    private CustomerOrderDao customerOrderDao;




}
