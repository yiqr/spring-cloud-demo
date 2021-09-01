package com.yiqr.modules.supermarket.goods.traditional.web;

import com.yiqr.modules.supermarket.goods.entity.GoodsInventoryEntity;
import com.yiqr.modules.supermarket.goods.properties.GoodsProperties;
import com.yiqr.modules.supermarket.goods.traditional.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yiqr
 * @Date: 2021/8/27 4:10 下午
 * @Description:
 */
@RefreshScope
@RestController
public class GoodsDemoWeb {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/getConfig")
    public GoodsProperties getConfig() {
        return goodsService.getGoodsProperties();
    }


    @GetMapping("/get_goods")
    public GoodsInventoryEntity getGoods(@RequestParam("goodsName") String goodsName) {
        return goodsService.getGoods(goodsName);
    }

    @GetMapping("/get_goods_num")
    public Integer getGoodsNum(@RequestParam("goodsName") String goodsName) {
        return goodsService.getGoodsNum(goodsName);
    }

    @GetMapping("/reduce_goods_inventory")
    public void reduceInventory(@RequestParam("goodsName") String goodsName, @RequestParam("num") Integer num) {
        goodsService.reduceInventory(goodsName, num);
    }

}
