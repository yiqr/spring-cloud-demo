package com.yiqr.modules.supermarket.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:49 下午
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "supermarket.pay.info")
public class PayProperties {
    private String name;
    private String version;
}
