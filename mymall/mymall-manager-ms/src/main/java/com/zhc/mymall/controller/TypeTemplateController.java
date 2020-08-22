package com.zhc.mymall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.pojo.RespBean;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.pojo.TypeTemplate;
import com.zhc.mymall.service.TypeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "TypeTemplateController", description = "模板管理")
public class TypeTemplateController {

    @Autowired
    private TypeTemplateService typeTemplateService;

    @GetMapping("/findAllTypeTemplate")
    @ApiOperation("查询所有类型模板")
    public List<TypeTemplate> findAllTypeTemplate() {
        System.out.println("/findAllTypeTemplate");
        List<TypeTemplate> typeTemplateList = typeTemplateService.findAllTypeTemplate();
        return typeTemplateList;
    }

    @GetMapping("/findTypeTemplateByPage")
    @ApiOperation("分页查询所有类型模板")
    public ResultPage findTypeTemplateByPage(int pageNum, int pageSize) {
        System.out.println("/findTypeTemplateByPage");
        PageHelper.startPage(pageNum, pageSize);
        Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateService.findAllTypeTemplate();
        for (TypeTemplate typeTemplate : page.getResult()) {
            typeTemplate.getSpecIds();
        }
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(page.getResult());
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }

    @GetMapping("/selectTypeTemplateById/{id}")
    @ApiOperation("根据id查询类型模板")
    public TypeTemplate selectTypeTemplateById(@PathVariable Long id){
        System.out.println("/selectTypeTemplateById");
        System.out.println("id");
        TypeTemplate typeTemplate = typeTemplateService.findTypeTemplateById(id);
        return typeTemplate;
    }

    /**
     * 增加
     * @param typeTemplate
     * @return
     */
    @PostMapping("/addTypeTemplate")
    @ApiOperation("添加类型模板")
    public RespBean add(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.addTypeTemplate(typeTemplate);
            return RespBean.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    /**
     * 修改
     * @param typeTemplate
     * @return
     */
    @PostMapping("/updateTypeTemplate")
    @ApiOperation("修改类型模板")
    public RespBean update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.updateTypeTemplate(typeTemplate);
            return RespBean.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/deleteTypeTemplates")
    @ApiOperation("删除类型模板")
    public RespBean deleteTypeTemplates(Long[] ids){
        System.out.println("/deleteTypeTemplates");
        System.out.println(ids.toString());

        try {
            typeTemplateService.deleteTypeTemplates(ids);
            return RespBean.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @RequestMapping("/searchByTypeTemplates")
    @ApiOperation("查询类型模板并分页")
    public ResultPage search(@RequestBody TypeTemplate typeTemplate, int page, int rows  ){
        System.out.println("/searchByTypeTemplates");
        return typeTemplateService.findPage(typeTemplate, page, rows);
    }

    @GetMapping("/findBySpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }


}
