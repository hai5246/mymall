package com.zhc.mymall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.mapper.SpecificationMapper;
import com.zhc.mymall.mapper.SpecificationOptionMapper;
import com.zhc.mymall.pojo.*;
import com.zhc.mymall.pojogroup.SpecificationGroup;
import com.zhc.mymall.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询所有规格信息
     * @return
     */
    @Override
    public List<Specification> findAllSpecification() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 根据id查询所有规格选项信息
     * @param id
     * @return
     */
    @Override
    public List<SpecificationOption> findAllSpecificationOption(Long id) {
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        return specificationOptionMapper.selectByExample(example);
    }

    /**
     * 根据id查询规格及其选项
     * @param id
     * @return
     */
    @Override
    public SpecificationGroup findSpecificationGroupById(Long id) {
        Specification specification = specificationMapper.selectByPrimaryKey(id);
        //查询对应的选项
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);

        List<SpecificationOption> options = specificationOptionMapper.selectByExample(example);
        //返回组合类
        SpecificationGroup specificationGroup = new SpecificationGroup();
        specificationGroup.setSpecificationOptionList(options);
        specificationGroup.setSpecification(specification);
        return specificationGroup;
    }

    /**
     * 添加规格及选项
     * @param specificationGroup
     */
    @Override
    public void addSpecificationGroup(SpecificationGroup specificationGroup) {
        //获取规格
        Specification specification = specificationGroup.getSpecification();
        System.out.println(specification);
        specificationMapper.insert(specification);
        //获取选项列表
        List<SpecificationOption> specificationOptionList = specificationGroup.getSpecificationOptionList();
        for (SpecificationOption option : specificationOptionList) {
            //为空则不添加
            if (option.getOptionName() != null && option.getOptionName() != ""){
                option.setSpecId(specification.getId());
                System.out.println(option);
                specificationOptionMapper.insert(option);
            }
        }
    }

    /**
     * 修改规格及其选项
     * @param specificationGroup
     */
    @Override
    public void updateSpecificationGroup(SpecificationGroup specificationGroup) {
        Specification specification = specificationGroup.getSpecification();
        //根据id修改
        specificationMapper.updateByPrimaryKey(specification);
        //先删除
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specification.getId());
        specificationOptionMapper.deleteByExample(example);
        //再添加
        List<SpecificationOption> specificationOptionList = specificationGroup.getSpecificationOptionList();
        for (SpecificationOption option : specificationOptionList) {
            if (option.getOptionName() != null && option.getOptionName() != ""){
                option.setSpecId(specification.getId());
                specificationOptionMapper.insert(option);
            }
        }
    }

    /**
     * 删除商品规格及其选项信息
     * @param ids
     */
    @Override
    public void deleteSpecificationAndOption(Long[] ids) {
        for (Long id : ids) {
            //删除规格
            specificationMapper.deleteByPrimaryKey(id);
            //删除选项
            SpecificationOptionExample example = new SpecificationOptionExample();
            SpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }

    @Override
    public List<Map> getSpecificationMap() {
        return specificationMapper.selectOptionList();
    }

    @Override
    public ResultPage searchBySpecification(Specification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        System.out.println(pageNum);
        System.out.println(pageSize);

        SpecificationExample example = new SpecificationExample();
        SpecificationExample.Criteria criteria = example.createCriteria();

        if (specification != null){
            if (specification.getSpecName() != null && specification.getSpecName().length()>0){
                System.out.println("getSpecName" + specification.getSpecName());
                criteria.andSpecNameLike("%"+ specification.getSpecName() +"%");
            }
        }else{
            System.out.println("specification为空");
        }

        Page<Specification> page= (Page<Specification>)specificationMapper.selectByExample(example);
        ResultPage resultPage = new ResultPage();
        resultPage.setTotal(page.getTotal());
        resultPage.setRows(page.getResult());

        for (Specification specification1 : page.getResult()) {
            System.out.println(specification1);
        }

        return resultPage;
    }
}
