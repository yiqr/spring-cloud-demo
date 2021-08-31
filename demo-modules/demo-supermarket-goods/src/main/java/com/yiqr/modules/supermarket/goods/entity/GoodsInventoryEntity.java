package com.yiqr.modules.supermarket.goods.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/26 9:04 下午
 * @Description: 商品库存
 */
@Data
@Entity
@Table(
        name = "goods_inventory",
        schema = "demo-supermarket-goods",
        uniqueConstraints = {
                @UniqueConstraint(name = "KEY_author_PRIMARY", columnNames = {"id"})
        }
)
public class GoodsInventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, precision = 10)
    private Integer id;

    @Column(name = "goods_name", length = 40)
    private String goodsName;

    @NotNull
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "goods_num", length = 5)
    private Integer goodsNum;
}
