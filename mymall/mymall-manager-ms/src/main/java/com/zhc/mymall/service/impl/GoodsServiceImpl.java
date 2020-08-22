package com.zhc.mymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.mapper.*;
import com.zhc.mymall.pojo.*;
import com.zhc.mymall.pojogroup.GoodsGroup;
import com.zhc.mymall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public List<Goods> findAllGoods() {
        return goodsMapper.selectByExample(null);
    }

    @Override
    public Goods findOneByGoods(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(GoodsGroup goodsGroup) {
        goodsGroup.getGoods().setAuditStatus("0"); //设置审核的状态
        goodsMapper.insert(goodsGroup.getGoods());

        goodsGroup.getGoodsDesc().setGoodsId(goodsGroup.getGoods().getId());
        goodsDescMapper.insert(goodsGroup.getGoodsDesc());

        setItemList(goodsGroup);//保存sku信息
    }

    private void setItemList(GoodsGroup goodsGroup){
        System.out.println("是否启用规格：" + goodsGroup.getGoods().getIsEnableSpec());
        if("1".equals(goodsGroup.getGoods().getIsEnableSpec())){
            // 启用规格
            // 保存SKU列表的信息:
            for(Item item:goodsGroup.getItemList()){
                // 设置SKU的数据：
                // item.setTitle(title);
                String title = goodsGroup.getGoods().getGoodsName();
                Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
                for (String key : map.keySet()) {
                    title+= " "+map.get(key);
                }
                item.setTitle(title);

                setValue(goodsGroup,item);

                itemMapper.insert(item);
            }
        }else{
            // 没有启用规格
            Item item = new Item();

            item.setTitle(goodsGroup.getGoods().getGoodsName());

            item.setPrice(goodsGroup.getGoods().getPrice());

            item.setNum(999);

            item.setStatus("0");

            item.setIsDefault("1");
            item.setSpec("{}");

            setValue(goodsGroup,item);
            itemMapper.insert(item);
        }
    }

    private void setValue(GoodsGroup goodsGroup,Item item){

        List<Map> imageList = JSON.parseArray(goodsGroup.getGoodsDesc().getItemImages(),Map.class);

        if(imageList.size()>0){
            item.setImage((String)imageList.get(0).get("url"));
        }

        // 保存三级分类的ID:
        item.setCategoryid(goodsGroup.getGoods().getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        // 设置商品ID
        item.setGoodsId(goodsGroup.getGoods().getId());
        item.setSellerId(goodsGroup.getGoods().getSellerId());
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goodsGroup.getGoods().getCategory3Id());

        item.setCategory(itemCat.getName());

        Brand brand = brandMapper.selectByPrimaryKey(goodsGroup.getGoods().getBrandId());

        item.setBrand(brand.getName());

        Seller seller = sellerMapper.selectByPrimaryKey(goodsGroup.getGoods().getSellerId());

        item.setSeller(seller.getNickName());
    }

    @Override
    public void deleteGoods(Long[] ids) {
        for(Long id:ids){
//			goodsMapper.deleteByPrimaryKey(id);
            Goods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete("1");
            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public ResultPage searchGoods(Goods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        GoodsExample example=new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();

        if(goods!=null){
            if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
                System.out.println("设置sellerId: " + goods.getSellerId());
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
                criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
            }
        }

        Page<Goods> page= (Page<Goods>)goodsMapper.selectByExample(example);
        System.out.println("##### "+page.getResult());
        System.out.println(page.getTotal());
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(page.getResult());
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            Goods tbGoods = goodsMapper.selectByPrimaryKey(id);

            tbGoods.setAuditStatus(status);

            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public List<Item> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status) {
        System.out.println("Goods Id列表：" + goodsIds);
        System.out.println("状态：" + status);
        ItemExample example=new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(status);//状态
        criteria.andGoodsIdIn(Arrays.asList(goodsIds));//指定条件：SPUID集合

        return itemMapper.selectByExample(example);
    }
}
