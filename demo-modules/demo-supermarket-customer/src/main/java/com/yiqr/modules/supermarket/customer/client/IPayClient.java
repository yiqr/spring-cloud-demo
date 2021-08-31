package com.yiqr.modules.supermarket.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/31 3:08 下午
 * @Description:
 */
@FeignClient("supermarket-pay")
public interface IPayClient {

    @GetMapping("/create_order")
    String createOrder(@RequestParam("transactionAmount") BigDecimal transactionAmount);
}