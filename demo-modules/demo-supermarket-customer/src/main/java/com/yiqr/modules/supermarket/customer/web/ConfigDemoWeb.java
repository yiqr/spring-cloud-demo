package com.yiqr.modules.supermarket.customer.web;

import com.yiqr.modules.supermarket.customer.properties.CustomerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 4:10 下午
 * @Description:
 */
@RestController
public class ConfigDemoWeb {
    @Autowired
    private CustomerProperties customerProperties;

    @GetMapping("/getConfig")
    public CustomerProperties getConfig(){
        return customerProperties;
    }
}
