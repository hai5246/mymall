package com.zhc.mymall.service;

import com.zhc.mymall.pojo.Content;

import java.util.List;

public interface ContentService {
    List<Content> findAllContents();

    Content findContentById(Long id);

    void addContent(Content content);

    void updateContent(Content content);

    void deleteContents(Long[] ids);

    List<Content> findByCategoryId(Long categoryId);
}
