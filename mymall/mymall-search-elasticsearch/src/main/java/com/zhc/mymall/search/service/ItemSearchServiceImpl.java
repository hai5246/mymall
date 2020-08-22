package com.zhc.mymall.search.service;


import com.zhc.mymall.pojo.Item;
import com.zhc.mymall.search.document.EsItem;
import com.zhc.mymall.search.repository.EsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private EsItemRepository esItemRepository;

    @Override
    public void importList(List<EsItem> list) {
        for (EsItem o : list) {
            esItemRepository.save(o);
        }
    }

    @Override
    public void deleteByGoodsIds(List goodsIds) {

    }
}
