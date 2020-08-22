package com.zhc.mymall.pojogroup;

import com.zhc.mymall.pojo.Goods;
import com.zhc.mymall.pojo.GoodsDesc;
import com.zhc.mymall.pojo.Item;

import java.io.Serializable;
import java.util.List;

public class GoodsGroup implements Serializable {
    private Goods goods; //商品信息
    private GoodsDesc goodsDesc; //商品拓展信息
    private List<Item> itemList; //SKU(规格)信息列表

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
