package com.yiqr.modules.supermarket.customer.dao;

import com.yiqr.modules.supermarket.customer.entity.CustomerAccountEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: yiqr
 * @Date: 2021/8/31 6:23 下午
 * @Description:
 */
@Repository
public interface CustomerAccountDao extends PagingAndSortingRepository<CustomerAccountEntity, Integer>, QuerydslPredicateExecutor<CustomerAccountEntity> {
}
