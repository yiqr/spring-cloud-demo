package com.yiqr.modules.supermarket.customer.traditional.web;

import com.yiqr.modules.supermarket.customer.properties.CustomerProperties;
import com.yiqr.modules.supermarket.customer.traditional.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 4:10 下午
 * @Description:
 */
@RestController
public class CustomerOrderDemoWeb {

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("/getConfig")
    public CustomerProperties getConfig() {
        return customerOrderService.getCustomer();
    }


    @GetMapping("/shopping")
    public Object shopping(@RequestParam("customerName") String customerName, @RequestParam("goodsNum") Integer goodsNum, @RequestParam("goodsName") String goodsName) {
        customerOrderService.shopping(customerName, goodsNum, goodsName);
        return "ok";
    }
}
