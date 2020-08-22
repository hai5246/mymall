package com.zhc.mymall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.pojo.Content;
import com.zhc.mymall.pojo.RespBean;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "ContentController", description = "广告管理接口")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping("/findContentByPage")
    @ApiOperation("分页查找广告")
    public ResultPage findContentByPage(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        Page<Content> page = (Page<Content>) contentService.findAllContents();
        ResultPage resultPage = new ResultPage();
        resultPage.setTotal(page.getTotal());
        resultPage.setRows(page.getResult());
        return resultPage;
    }

    @GetMapping("/selectContentById/{id}")
    @ApiOperation("根据id查询广告")
    public Content selectContentById(@PathVariable Long id){
        System.out.println("/selectContentById");
        System.out.println("id");
        Content brand = contentService.findContentById(id);
        return brand;
    }

    @PostMapping("/addContent")
    @ApiOperation("添加广告")
    public RespBean addContent(@RequestBody @ApiParam Content content){
        System.out.println("/addContent");
        System.out.println(content);
        try{
            contentService.addContent(content);
            return RespBean.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    @PostMapping("/updateContent")
    @ApiOperation("修改广告")
    public RespBean updateContent(@RequestBody @ApiParam Content content){
        System.out.println("/updateContent");
        System.out.println(content);
        try{
            contentService.updateContent(content);
            return RespBean.ok("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/deleteContent")
    @ApiOperation("删除品牌信息")
    public RespBean deleteContent(Long[] ids){
        System.out.println("/deleteContent");
        System.out.println(ids.toString());
        try {
            contentService.deleteContents(ids);
            return RespBean.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }


}
