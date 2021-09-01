package com.yiqr.modules.supermarket.customer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 3:46 下午
 * @Description:
 */
@Data
@Entity
@Table(
        name = "customer_message",
        schema = "demo-supermarket-customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "KEY_author_PRIMARY", columnNames = {"id"})
        }
)
public class CustomerMessageEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "tx_no", length = 255)
    private String txNo;

    //消息队列
    @Column(name = "consumer_queue", length = 255)
    private String consumerQueue;

    //消息内容
    @Column(name = "message_body", length = 255)
    private String messageBody;

    //重试次数
    @Column(name = "resend_times")
    private Integer resendTimes;

    //状态
    @Column(name = "status")
    private Integer status;

    //消息确认时间
    @Column(name = "confirm_time")
    private Date confirmTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
