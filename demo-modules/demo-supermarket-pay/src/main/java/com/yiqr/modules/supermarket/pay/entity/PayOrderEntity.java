package com.yiqr.modules.supermarket.pay.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Column(name = "id", nullable = false, precision = 10)
    private Integer id;

    @Size(max = 40)
    @Column(name = "transaction_no", length = 40)
    private String transactionNo;

    @Column(name = "transaction_amount", precision = 10, scale = 2)
    private BigDecimal transactionAmount;

}
