package com.yiqr.modules.supermarket.pay.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/26 9:16 下午
 * @Description: 支付订单
 */
@Data
@Entity
@Table(
        name = "pay_order",
        schema = "demo-supermarket-pay",
        uniqueConstraints = {
                @UniqueConstraint(name = "KEY_author_PRIMARY", columnNames = {"id"})
        }
)
public class PayOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, precision = 10)
    private Integer id;

    @Column(name = "transaction_no", length = 40)
    private String transactionNo;

    @Column(name = "transaction_amount", precision = 10, scale = 2)
    private BigDecimal transactionAmount;

}
