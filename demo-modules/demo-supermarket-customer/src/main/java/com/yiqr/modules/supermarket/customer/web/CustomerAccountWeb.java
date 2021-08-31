package com.yiqr.modules.supermarket.customer.web;

import com.yiqr.modules.supermarket.customer.entity.CustomerAccountEntity;
import com.yiqr.modules.supermarket.customer.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/31 6:25 下午
 * @Description:
 */
@RestController
public class CustomerAccountWeb {

    @Autowired
    private CustomerAccountService customerAccountService;

    @GetMapping("/create_customer")
    public CustomerAccountEntity createCustomer(@RequestParam("name") String name, @RequestParam("amt") BigDecimal amt) {
        return customerAccountService.createCustomer(name, amt);
    }
}
