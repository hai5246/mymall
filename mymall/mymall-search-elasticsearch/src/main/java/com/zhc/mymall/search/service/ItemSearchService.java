package com.zhc.mymall.search.service;

import com.zhc.mymall.pojo.Item;
import com.zhc.mymall.search.document.EsItem;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    void importList(List<EsItem> itemList);

    void deleteByGoodsIds(List<Long> asList);
}
