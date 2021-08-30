package com.yiqr.modules.supermarket.customer.dao;

import com.yiqr.modules.supermarket.customer.entity.CustomerOrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 3:22 下午
 * @Description:
 */
@Repository
public interface CustomerOrderDao extends PagingAndSortingRepository<CustomerOrderEntity, Integer> {
}
