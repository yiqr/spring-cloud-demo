package com.yiqr.modules.supermarket.goods.dao;

import com.yiqr.modules.supermarket.goods.entity.GoodsInventoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:56 下午
 * @Description:
 */
@Repository
public interface GoodsInventoryDao extends PagingAndSortingRepository<GoodsInventoryEntity, Integer> {
}
