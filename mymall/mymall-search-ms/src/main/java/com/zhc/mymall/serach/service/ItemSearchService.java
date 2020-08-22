package com.zhc.mymall.serach.service;

import com.zhc.mymall.pojo.Item;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    Map searchItem(Map searchMap);

    void importList(List<Item> itemList);

    void deleteByGoodsIds(List<Long> asList);
}
