package com.yiqr.modules.supermarket.goods.traditional.service;

import com.yiqr.modules.supermarket.goods.dao.GoodsInventoryDao;
import com.yiqr.modules.supermarket.goods.entity.GoodsInventoryEntity;
import com.yiqr.modules.supermarket.goods.entity.QGoodsInventoryEntity;
import com.yiqr.modules.supermarket.goods.properties.GoodsProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @Auther: yiqr
 * @Date: 2021/8/30 5:55 下午
 * @Description:
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsInventoryDao goodsInventoryDao;
    @Autowired
    private GoodsProperties goodsProperties;

    public GoodsProperties getGoodsProperties() {
        return goodsProperties;
    }


    /**
     * 获取商品信息
     *
     * @param goodsName
     * @return
     */
    public GoodsInventoryEntity getGoods(String goodsName) {
        Optional<GoodsInventoryEntity> entityOptional = goodsInventoryDao.findOne(QGoodsInventoryEntity.goodsInventoryEntity.goodsName.eq(goodsName));
        return !entityOptional.isPresent() ? null : entityOptional.get();
    }

    /**
     * 获取商品数量
     *
     * @param goodsName
     * @return
     */
    public Integer getGoodsNum(String goodsName) {
        Optional<GoodsInventoryEntity> entityOptional = goodsInventoryDao.findOne(QGoodsInventoryEntity.goodsInventoryEntity.goodsName.eq(goodsName));
        return !entityOptional.isPresent() ? 0 : entityOptional.get().getGoodsNum();
    }


    /**
     * 减少库存
     *
     * @param goodsName
     * @param num
     */
    @Transactional
    public void reduceInventory(String goodsName, Integer num) {
        if (StringUtils.isEmpty(goodsName)) {
            throw new RuntimeException("goodsName not be null.");
        }
        Optional<GoodsInventoryEntity> entityOptional = goodsInventoryDao.findOne(QGoodsInventoryEntity.goodsInventoryEntity.goodsName.eq(goodsName));
        if (!entityOptional.isPresent()) {
            throw new RuntimeException("商品不存在");
        }
        GoodsInventoryEntity goodsInventoryEntity = entityOptional.get();
        Integer goodsNum = goodsInventoryEntity.getGoodsNum();
        if (Objects.isNull(goodsNum)) goodsNum = 0;
        if (goodsNum < num) {
            throw new RuntimeException("商品库存不足");
        }
        goodsInventoryEntity.setGoodsNum(goodsNum - num);
        goodsInventoryDao.save(goodsInventoryEntity);
    }

}
