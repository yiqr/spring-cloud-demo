package com.yiqr.modules.supermarket.pay.web;

import com.yiqr.modules.supermarket.pay.properties.PayProperties;
import com.yiqr.modules.supermarket.pay.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 4:10 下午
 * @Description:
 */
@RefreshScope
@RestController
public class PayOrderDemoWeb {
    @Autowired
    private PayOrderService payOrderService;

    @GetMapping("/getConfig")
    public PayProperties getConfig(){
        return payOrderService.getConfig();
    }

    @GetMapping("/create_order")
    public String createOrder(@RequestParam("transactionAmount") BigDecimal transactionAmount) {
       return payOrderService.createOrder(transactionAmount);
    }

}
