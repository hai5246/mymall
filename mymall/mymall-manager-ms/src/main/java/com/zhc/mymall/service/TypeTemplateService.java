package com.zhc.mymall.service;

import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    List<TypeTemplate> findAllTypeTemplate();

    TypeTemplate findTypeTemplateById(Long id);

    void addTypeTemplate(TypeTemplate typeTemplate);

    void updateTypeTemplate(TypeTemplate typeTemplate);

    void deleteTypeTemplates(Long[] ids);

    ResultPage findPage(TypeTemplate typeTemplate, int page, int rows);

    List<Map> findSpecList(Long id);
}
