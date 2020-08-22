package com.zhc.mymall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.mapper.ItemCatMapper;
import com.zhc.mymall.pojo.ItemCat;
import com.zhc.mymall.pojo.ItemCatExample;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatExample example = new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(parentId);

        return itemCatMapper.selectByExample(example);
    }

    @Override
    public ItemCat findOneByItemCat(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAllByItemCat() {
        return itemCatMapper.selectByExample(null);
    }

    @Override
    public ResultPage findPageByItemCat(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<ItemCat> page=   (Page<ItemCat>) itemCatMapper.selectByExample(null);
        ResultPage resultPage = new ResultPage();
        resultPage.setTotal(page.getTotal());
        resultPage.setRows(page.getResult());
        return resultPage;
    }

    @Override
    public void addItemCat(ItemCat itemCat) {
        itemCatMapper.insert(itemCat);
    }

    @Override
    public void updateItemCat(ItemCat itemCat) {
        itemCatMapper.updateByPrimaryKey(itemCat);
    }

    @Override
    public void deleteItemCats(Long[] ids) {
        for (Long id : ids) {
            itemCatMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public ResultPage searchByItemCat(ItemCat itemCat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ItemCatExample example=new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();

        if(itemCat!=null){
            if(itemCat.getName()!=null && itemCat.getName().length()>0){
                criteria.andNameLike("%"+itemCat.getName()+"%");
            }

        }

        Page<ItemCat> page= (Page<ItemCat>)itemCatMapper.selectByExample(example);
        ResultPage resultPage = new ResultPage();
        resultPage.setTotal(page.getTotal());
        resultPage.setRows(page.getResult());
        return resultPage;
    }
}
