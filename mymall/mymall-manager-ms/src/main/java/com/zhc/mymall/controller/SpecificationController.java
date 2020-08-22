package com.zhc.mymall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.pojo.*;
import com.zhc.mymall.pojogroup.SpecificationGroup;
import com.zhc.mymall.service.SpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "SpecificationController", description = "商品规格管理接口")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("/getSpecificationByPage")
    @ApiOperation("分页查询规格")
    public ResultPage getSpecificationByPage(int pageNum, int pageSize) {
        System.out.println("/getSpecificationByPage");
        PageHelper.startPage(pageNum, pageSize);
        Page<Specification> page = (Page<Specification>) specificationService.findAllSpecification();
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(page.getResult());
        resultPage.setTotal(page.getTotal());
        return resultPage;

    }

    @GetMapping("/selectSpecificationGroupById/{id}")
    @ApiOperation("根据id查询规格及选项")
    public SpecificationGroup selectBrandById(@PathVariable Long id){
        System.out.println("/selectBrandById");
        System.out.println(id);
        SpecificationGroup specificationGroup = specificationService.findSpecificationGroupById(id);
        return specificationGroup;
    }

    @PostMapping("/addSpecificationGroup")
    @ApiOperation("添加规格及其选项")
    public RespBean addSpecificationGroup(@RequestBody @ApiParam SpecificationGroup specificationGroup){
        System.out.println("/addSpecificationGroup");
        try{
            specificationService.addSpecificationGroup(specificationGroup);
            return RespBean.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    @PostMapping("/updateSpecificationGroup")
    @ApiOperation("修改规格及其选项")
    public RespBean updateSpecificationGroup(@RequestBody @ApiParam SpecificationGroup specificationGroup){
        System.out.println("/updateSpecificationGroup");
        try{
            specificationService.updateSpecificationGroup(specificationGroup);
            return RespBean.ok("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("修改失败");
        }
    }


    @GetMapping("/deleteSpecification")
    @ApiOperation("删除规格及其选项")
    public RespBean deleteSpecification(Long[] ids){
        System.out.println("/deleteSpecification");
        try{
            specificationService.deleteSpecificationAndOption(ids);
            return RespBean.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("删除失败");
        }

    }

    @GetMapping("/getSpecificationMap")
    @ApiOperation("查询规格数据")
    public List<Map> getSpecificationMap() {
        System.out.println("/getSpecificationMap");
        return specificationService.getSpecificationMap();

    }

    @PostMapping("/searchBySpecification")
    @ApiOperation("查询规格模板并分页")
    public ResultPage searchBySpecification(@RequestBody Specification specification, int pageNum, int pageSize  ){
        System.out.println(specification);
        System.out.println("/searchBySpecification");
        return specificationService.searchBySpecification(specification, pageNum, pageSize);
    }


}
