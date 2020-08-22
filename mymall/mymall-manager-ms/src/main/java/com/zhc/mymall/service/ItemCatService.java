package com.zhc.mymall.service;

import com.zhc.mymall.pojo.ItemCat;
import com.zhc.mymall.pojo.ResultPage;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ItemCatService {
    List<ItemCat> findByParentId(Long parentId);

    ItemCat findOneByItemCat(Long id);

    List<ItemCat> findAllByItemCat();

    ResultPage findPageByItemCat(int page, int rows);

    void addItemCat(ItemCat itemCat);

    void updateItemCat(ItemCat itemCat);

    void deleteItemCats(Long[] ids);

    ResultPage searchByItemCat(ItemCat itemCat, int pageNum, int pageSize);
}
