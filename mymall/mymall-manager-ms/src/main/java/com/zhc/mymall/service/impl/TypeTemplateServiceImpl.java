package com.zhc.mymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.mapper.SpecificationOptionMapper;
import com.zhc.mymall.mapper.TypeTemplateMapper;
import com.zhc.mymall.pojo.*;
import com.zhc.mymall.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public List<TypeTemplate> findAllTypeTemplate() {
        return typeTemplateMapper.selectByExample(null);
    }

    @Override
    public TypeTemplate findTypeTemplateById(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addTypeTemplate(TypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }

    @Override
    public void updateTypeTemplate(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }

    @Override
    public void deleteTypeTemplates(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public ResultPage findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TypeTemplateExample example=new TypeTemplateExample();
        TypeTemplateExample.Criteria criteria = example.createCriteria();

        if(typeTemplate!=null){
            System.out.println(typeTemplate);
            if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
                criteria.andNameLike("%"+typeTemplate.getName()+"%");
            }
            if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
                criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
            }
            if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
                criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
            }
            if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
                criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
            }

        }

        Page<TypeTemplate> page= (Page<TypeTemplate>)typeTemplateMapper.selectByExample(example);
        ResultPage resultPage = new ResultPage();
        resultPage.setTotal(page.getTotal());
        resultPage.setRows(page.getResult());
        for (TypeTemplate template : (List<TypeTemplate>) resultPage.getRows()) {
            System.out.println(template);
        }
        System.out.println(resultPage.getTotal());
        return resultPage;
    }

    @Override
    public List<Map> findSpecList(Long id) {
        //根据ID查询到模板对象
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        // 获得规格的数据spec_ids
        String specIds = typeTemplate.getSpecIds();// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        // 将specIds的字符串转成JSON的List<Map>
        List<Map> list = JSON.parseArray(specIds, Map.class);
        // 获得每条记录:
        for (Map map : list) {
            // 根据规格的ID 查询规格选项的数据:
            // 设置查询条件:
            SpecificationOptionExample example = new SpecificationOptionExample();
            SpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));

            List<SpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
            //查询tb_specification_oprion表，加载option_name字段，然后封装到map中
            map.put("options", specOptionList);//{"id":27,"text":"网络",options:[{id：xxx,optionName:移动2G}]}
        }
        return list;
    }
}
