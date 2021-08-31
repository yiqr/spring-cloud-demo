package com.yiqr.modules.supermarket.customer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Auther: yiqr
 * @Date: 2021/8/31 6:15 下午
 * @Description:
 */
@Data
@Entity
@Table(
        name = "customer_account",
        schema = "demo-supermarket-customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "KEY_author_PRIMARY", columnNames = {"id"})
        }
)
public class CustomerAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, precision = 10)
    private Integer id;

    @NotNull
    @Column(name = "customer_name", length = 255)
    private String customerName;

    @Column(name = "balance_amt", precision = 10, scale = 2)
    private BigDecimal balanceAmt;
}
