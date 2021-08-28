package com.yiqr.modules.supermarket.customer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/26 8:39 下午
 * @Description: 客户订单
 */
@Data
@Entity
@Table(
        name = "customer_order",
        schema = "demo-supermarket-customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "KEY_author_PRIMARY", columnNames = {"id"})
        }
)
public class CustomerOrderEntity {

    @Id
    @NotNull
    @Column(name = "id", nullable = false, precision = 10)
    private Integer id;


    @Size(max = 255)
    @Column(name = "customer_name", length = 255)
    private String customerName;

    @Size(max = 40)
    @Column(name = "order_no", length = 40)
    private String orderNo;

    @Size(max = 40)
    @Column(name = "goods_name", length = 40)
    private String goodsName;

    @Size(max = 5)
    @Column(name = "goods_num", length = 5)
    private Integer goodsNum;

    @NotNull
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @NotNull
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Size(max = 40)
    @Column(name = "transaction_no", length = 40)
    private String transactionNo;
}
