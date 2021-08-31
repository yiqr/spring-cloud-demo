package com.yiqr.modules.supermarket.customer.client.mode;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/26 9:04 下午
 * @Description: 商品库存
 */
@Data
public class GoodsInventoryEntity {
    private Integer id;
    private String goodsName;
    private BigDecimal unitPrice;
    private Integer goodsNum;
}
