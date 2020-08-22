package com.zhc.mymall.serach.controller;

import com.zhc.mymall.serach.service.ItemSearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags = "ItemSearchController" , description = "商品列表接口")
public class ItemSearchController {
    @Autowired
    private ItemSearchService itemSearchService;

    @RequestMapping("/searchItem")
    public Map searchItem(@RequestBody Map searchMap){
        System.out.println("/searchItem");
        return itemSearchService.searchItem(searchMap);
    }

}
