package com.zhc.mymall.service.impl;

import com.zhc.mymall.mapper.BrandMapper;
import com.zhc.mymall.pojo.Brand;
import com.zhc.mymall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 获取所有品牌
     * @return
     */
    @Override
    public List<Brand> findAllBrands() {
        return brandMapper.selectByExample(null);
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @Override
    public void addBrand(Brand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @Override
    public Brand findBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 删除品牌信息
     * @param ids
     * @return
     */
    @Override
    public void deleteNews(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<Map> getBrandMap() {
        return brandMapper.selectOptionList();
    }
}
