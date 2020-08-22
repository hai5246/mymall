package com.zhc.mymall.service.impl;

import com.zhc.mymall.mapper.ContentCategoryMapper;
import com.zhc.mymall.pojo.ContentCategory;
import com.zhc.mymall.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    /**
     * 获取所有的广告类型信息
     * @return
     */
    @Override
    public List<ContentCategory> findAllContentCategory() {
        return contentCategoryMapper.selectByExample(null);
    }
}
