package com.yiqr.modules.supermarket.goods.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:49 下午
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "supermarket.goods.info")
public class GoodsProperties {
    private String name;
    private BigDecimal unitPrice;
}
