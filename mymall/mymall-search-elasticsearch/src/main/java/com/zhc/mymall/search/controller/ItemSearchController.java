package com.zhc.mymall.search.controller;

import com.zhc.mymall.search.document.EsItem;
import com.zhc.mymall.search.repository.EsItemRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "ItemSearchController" , description = "商品列表接口")
public class ItemSearchController {
    @Autowired
    private EsItemRepository esItemRepository;

    @RequestMapping("/searchItem")
    public Map searchItem(@RequestBody Map searchMap){

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        System.out.println("/searchItem");

        String keywords = (String) searchMap.get("keywords");
        Page<EsItem> page = esItemRepository.findByKeyword(keywords, null);

        Map map = new HashMap();
        map.put("rows",page.getContent());

        return map;
    }

}
