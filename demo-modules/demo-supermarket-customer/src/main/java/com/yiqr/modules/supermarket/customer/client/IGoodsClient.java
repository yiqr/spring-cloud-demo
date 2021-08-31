package com.yiqr.modules.supermarket.customer.client;

import com.yiqr.modules.supermarket.customer.client.mode.GoodsInventoryEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: yiqr
 * @Date: 2021/8/31 3:08 下午
 * @Description:
 */
@FeignClient("supermarket-goods")
public interface IGoodsClient {

    @GetMapping("/get_goods")
    GoodsInventoryEntity getGoods(@RequestParam("goodsName") String goodsName);

    @GetMapping("/get_goods_num")
    Integer getGoodsNum(@RequestParam("goodsName") String goodsName);

    @GetMapping("/reduce_goods_inventory")
    void reduceInventory(@RequestParam("goodsName") String goodsName, @RequestParam("num") Integer num);
}