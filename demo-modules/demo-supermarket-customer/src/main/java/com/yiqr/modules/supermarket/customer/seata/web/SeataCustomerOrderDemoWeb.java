package com.yiqr.modules.supermarket.customer.seata.web;

import com.yiqr.modules.supermarket.customer.seata.service.SeateCustomerOrderDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 2:25 下午
 * @Description:
 */
@RestController
@RequestMapping("/seata")
public class SeataCustomerOrderDemoWeb {

    @Autowired
    SeateCustomerOrderDemoService customerOrderService;

    @GetMapping("/shopping")
    public Object shopping(@RequestParam("customerName") String customerName, @RequestParam("goodsNum") Integer goodsNum, @RequestParam("goodsName") String goodsName) {
        customerOrderService.shopping(customerName, goodsNum, goodsName);
        return "ok";
    }
}
