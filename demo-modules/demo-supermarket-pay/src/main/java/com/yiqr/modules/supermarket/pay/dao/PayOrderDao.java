package com.yiqr.modules.supermarket.pay.dao;

import com.yiqr.modules.supermarket.pay.entity.PayOrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:56 下午
 * @Description:
 */
@Repository
public interface PayOrderDao extends PagingAndSortingRepository<PayOrderEntity, Integer> {
}
