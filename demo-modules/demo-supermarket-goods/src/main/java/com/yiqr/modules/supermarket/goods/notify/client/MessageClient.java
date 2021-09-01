package com.yiqr.modules.supermarket.goods.notify.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 6:27 下午
 * @Description:
 */
@FeignClient("supermarket-customer")
public interface MessageClient {

    @GetMapping("/notify/message_handler_success")
    String messageHandlerSuccess(@RequestParam("messageId") String messageId);

    @GetMapping("/notify/message_handler_fail")
    String messageHandlerFail(@RequestParam("messageId") String messageId);
}
