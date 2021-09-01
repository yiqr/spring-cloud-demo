package com.yiqr.modules.supermarket.customer.dao;

import com.yiqr.modules.supermarket.customer.entity.CustomerMessageEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Auther: yiqr
 * @Date: 2021/9/1 4:02 下午
 * @Description:
 */
@Repository
public interface CustomerMessageDao extends PagingAndSortingRepository<CustomerMessageEntity, Serializable>, QuerydslPredicateExecutor<CustomerMessageEntity> {
}
