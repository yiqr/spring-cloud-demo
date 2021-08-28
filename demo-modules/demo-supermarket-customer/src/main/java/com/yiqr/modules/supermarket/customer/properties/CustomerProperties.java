package com.yiqr.modules.supermarket.customer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 4:57 下午
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "supermarket.customer.info")
public class CustomerProperties implements Serializable {
    private static final long serialVersionUID = 9061468792273712212L;
    public String name;
    public Integer age;
}
