package com.yiqr.modules.supermarket.pay.notify.consumer;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 5:42 下午
 * @Description:
 */
@Data
public class CustomerMessage implements Serializable {
    private static final long serialVersionUID = -239080254016219004L;
    private String id;
    private String txNo;
    //消息队列
    private String consumerQueue;
    //消息内容
    private String messageBody;
    //重试次数
    private Integer resendTimes;
    //状态
    private Integer status;
    //消息确认时间
    private Date confirmTime;
    private Date createTime;
    private Date updateTime;
}
