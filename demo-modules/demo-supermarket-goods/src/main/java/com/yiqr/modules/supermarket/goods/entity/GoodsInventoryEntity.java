package com.yiqr.modules.supermarket.goods.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull
    @Column(name = "id", nullable = false, precision = 10)
    private Integer id;

    @Size(max = 40)
    @Column(name = "goods_name", length = 40)
    private String goodsName;

    @Size(max = 5)
    @Column(name = "goods_num", length = 5)
    private Integer goodsNum;
}
