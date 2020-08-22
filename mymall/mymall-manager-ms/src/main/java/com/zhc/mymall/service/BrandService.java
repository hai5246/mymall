package com.zhc.mymall.service;

import com.zhc.mymall.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    List<Brand> findAllBrands();

    void addBrand(Brand brand);

    Brand findBrandById(Long id);

    void updateBrand(Brand brand);

    void deleteNews(Long[] ids);

    List<Map> getBrandMap();
}
