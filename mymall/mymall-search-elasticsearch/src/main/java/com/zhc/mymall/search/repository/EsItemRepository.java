package com.zhc.mymall.search.repository;

import com.zhc.mymall.search.document.EsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsItemRepository extends ElasticsearchRepository<EsItem, Long> {

    public Page<EsItem> findByTitleAndBrand(String title, String brand, Pageable pageable);
    public Page<EsItem> findByTitleOrBrand(String title, String brand, Pageable pageable);

    //多字段查询
    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"title\",\"brand\",\"category\"]}}")
    public Page<EsItem> findByKeyword(String keyword, Pageable pageable);
}
