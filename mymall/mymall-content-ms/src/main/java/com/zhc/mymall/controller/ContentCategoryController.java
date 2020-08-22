package com.zhc.mymall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.pojo.ContentCategory;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.service.ContentCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "ContentCategoryController", description = "广告类型管理接口")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @GetMapping("/findAllContentCategory")
    @ApiOperation("查找所有类别内容")
    public List<ContentCategory> findAllContentCategory(){
        System.out.println("/findAllContentCategory");
        List<ContentCategory> contentCategoryList = contentCategoryService.findAllContentCategory();
        return contentCategoryList;
    }

    @GetMapping("/findContentCategoryByPage")
    @ApiOperation("分页查找广告类别内容")
    public ResultPage findContentCategoryByPage(int pageNum, int pageSize){
        System.out.println("/findContentCategoryByPage");
        PageHelper.startPage(pageNum,pageSize);
        Page<ContentCategory> page = (Page<ContentCategory>)contentCategoryService.findAllContentCategory();
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(page.getResult());
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }


}
