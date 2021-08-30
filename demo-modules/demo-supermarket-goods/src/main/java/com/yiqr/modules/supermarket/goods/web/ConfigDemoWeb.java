package com.yiqr.modules.supermarket.goods.web;

import com.yiqr.modules.supermarket.goods.properties.GoodsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 4:10 下午
 * @Description:
 */
@RefreshScope
@RestController
public class ConfigDemoWeb {
    @Autowired
    private GoodsProperties goodsProperties;

    @GetMapping("/getConfig")
    public GoodsProperties getConfig() {
        return goodsProperties;
    }
}
