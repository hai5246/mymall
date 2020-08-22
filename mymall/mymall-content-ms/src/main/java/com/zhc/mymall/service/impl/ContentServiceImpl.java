package com.zhc.mymall.service.impl;

import com.zhc.mymall.mapper.ContentMapper;
import com.zhc.mymall.pojo.Content;
import com.zhc.mymall.pojo.ContentExample;
import com.zhc.mymall.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Content> findAllContents() {
        return contentMapper.selectByExample(null);
    }

    @Override
    public Content findContentById(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addContent(Content content) {
        contentMapper.insert(content);
        // 清除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }

    @Override
    public void updateContent(Content content) {
        contentMapper.updateByPrimaryKey(content);
        // 清除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }

    @Override
    public void deleteContents(Long[] ids) {
        for (Long id : ids) {
            Content content = contentMapper.selectByPrimaryKey(id);
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());

            contentMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 首次访问时，如果redis没有数据，就访问数据库，把数据存到redis
     * 后续访问时，直接查询redis
     *
     *   哈希结构
     *        Key  content
     *                     field            value
     *                     categoryid 1     List<Content>
     *
     */
    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        //redisTemplate.opsForHash(); List<String> list = (List<String>) redisTemplate.opsForHash().get("content",categoryId);
        // 加入缓存的代码:
        List<Content> list = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);

        if(list==null){
            System.out.println("查询数据库===================");
            ContentExample example = new ContentExample();
            ContentExample.Criteria criteria = example.createCriteria();
            // 有效广告:
            criteria.andStatusEqualTo("1");

            criteria.andCategoryIdEqualTo(categoryId);
            // 排序
            example.setOrderByClause("sort_order");

            list = contentMapper.selectByExample(example);

            redisTemplate.boundHashOps("content").put(categoryId, list);
        }else{
            System.out.println("从缓存中获取====================");
        }
        return list;
    }
}
