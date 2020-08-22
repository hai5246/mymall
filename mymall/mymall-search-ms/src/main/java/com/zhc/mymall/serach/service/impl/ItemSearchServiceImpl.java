package com.zhc.mymall.serach.service.impl;


import com.zhc.mymall.pojo.Item;
import com.zhc.mymall.serach.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired SolrTemplate solrTemplate;

    @Override
    public Map searchItem(Map searchMap) {
        Map map = new HashMap();

        Query query = new SimpleQuery("*:*");
        //item_keywords是solr的业务字段
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        ScoredPage<Item> scoredPage = solrTemplate.queryForPage("collection1", query, Item.class);

        map.put("rows", scoredPage.getContent());

        return map;
    }

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans("collection1", list);
        solrTemplate.commit("collection1");
    }

    @Override
    public void deleteByGoodsIds(List goodsIds) {
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("item_goodsid").in(goodsIds);
        query.addCriteria(criteria);
        solrTemplate.delete("collection1",query);
        solrTemplate.commit("collection1");
    }
}
