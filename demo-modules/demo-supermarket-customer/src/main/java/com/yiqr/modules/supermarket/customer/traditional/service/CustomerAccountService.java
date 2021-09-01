package com.yiqr.modules.supermarket.customer.traditional.service;

import com.yiqr.modules.supermarket.customer.dao.CustomerAccountDao;
import com.yiqr.modules.supermarket.customer.entity.CustomerAccountEntity;
import com.yiqr.modules.supermarket.customer.entity.QCustomerAccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Auther: yiqr
 * @Date: 2021/8/31 6:25 下午
 * @Description:
 */
@Service
public class CustomerAccountService {
    @Autowired
    private CustomerAccountDao customerAccountDao;

    public CustomerAccountEntity createCustomer(String name, BigDecimal amt) {
        CustomerAccountEntity customerAccountEntity = null;
        Optional<CustomerAccountEntity> optional = customerAccountDao.findOne(QCustomerAccountEntity.customerAccountEntity.customerName.eq(name));
        if (optional.isPresent()) {
            customerAccountEntity = optional.get();
            customerAccountEntity.setBalanceAmt(customerAccountEntity.getBalanceAmt().add(amt));
            customerAccountDao.save(customerAccountEntity);
        } else {
            customerAccountEntity = new CustomerAccountEntity();
            customerAccountEntity.setCustomerName(name);
            customerAccountEntity.setBalanceAmt(amt);
            customerAccountDao.save(customerAccountEntity);
        }
        return customerAccountEntity;
    }
}
