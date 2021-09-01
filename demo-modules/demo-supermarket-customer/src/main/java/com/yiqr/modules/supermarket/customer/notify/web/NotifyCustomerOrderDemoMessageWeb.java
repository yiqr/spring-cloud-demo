package com.yiqr.modules.supermarket.customer.notify.web;

import com.yiqr.modules.supermarket.customer.notify.service.NotifyCustomerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 6:23 下午
 * @Description:
 */
@RestController
@RequestMapping("/notify")
public class NotifyCustomerOrderDemoMessageWeb {

    @Autowired
    NotifyCustomerMessageService notifyCustomerMessageService;

    @GetMapping("/message_handler_success")
    public String messageHandlerSuccess(@RequestParam("messageId") String messageId) {
        notifyCustomerMessageService.messageHandlerSuccess(messageId);
        return "ok";
    }

    @GetMapping("/message_handler_fail")
    public String messageHandlerFail(@RequestParam("messageId") String messageId) {
        notifyCustomerMessageService.messageHandlerFail(messageId);
        return "ok";
    }

}
