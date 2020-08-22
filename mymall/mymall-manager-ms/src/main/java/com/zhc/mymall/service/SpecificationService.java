package com.zhc.mymall.service;

import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.pojo.Specification;
import com.zhc.mymall.pojo.SpecificationOption;
import com.zhc.mymall.pojo.TypeTemplate;
import com.zhc.mymall.pojogroup.SpecificationGroup;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    List<Specification> findAllSpecification();

    List<SpecificationOption> findAllSpecificationOption(Long id);

    SpecificationGroup findSpecificationGroupById(Long id);

    void addSpecificationGroup(SpecificationGroup specificationGroup);

    void updateSpecificationGroup(SpecificationGroup specificationGroup);

    void deleteSpecificationAndOption(Long[] ids);

    List<Map> getSpecificationMap();

    ResultPage searchBySpecification(Specification specification, int page, int rows);
}
