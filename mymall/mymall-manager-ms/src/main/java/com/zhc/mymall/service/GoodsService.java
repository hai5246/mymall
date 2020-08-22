package com.zhc.mymall.service;

import com.zhc.mymall.pojo.Goods;
import com.zhc.mymall.pojo.Item;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.pojogroup.GoodsGroup;

import java.util.List;

public interface GoodsService {
    void add(GoodsGroup goodsGroup);

    List<Goods> findAllGoods();

    Goods findOneByGoods(Long id);

    void deleteGoods(Long[] ids);

    ResultPage searchGoods(Goods goods, int page, int rows);

    void updateStatus(Long[] ids, String status);

    List<Item> findItemListByGoodsIdListAndStatus(Long[] ids, String status);
}
