package com.yiqr.modules.supermarket.goods.service;

import com.yiqr.modules.supermarket.goods.dao.GoodsInventoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:55 下午
 * @Description:
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsInventoryDao goodsInventoryDao;
}
