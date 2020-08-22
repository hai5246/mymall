package com.zhc.mymall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.pojo.Brand;
import com.zhc.mymall.pojo.RespBean;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "BrandController", description = "品牌管理接口")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/getAllBrands")
    @ApiOperation("获取所有品牌")
    public List<Brand> getAllBrands() {
        System.out.println("/getAllBrands");
        return brandService.findAllBrands();
    }

    @GetMapping("/findBrandByPage")
    @ApiOperation("分页查询品牌信息")
    public ResultPage findBrandByPage(int pageNum, int pageSize){
        System.out.println("/findBrandByPage");
        PageHelper.startPage(pageNum, pageSize);
        Page<Brand> page = (Page<Brand>) brandService.findAllBrands();
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(page.getResult());
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }

    @PostMapping("/addBrand")
    @ApiOperation("添加品牌")
    public RespBean addBrand(@RequestBody @ApiParam("品牌对象") Brand brand) {
        System.out.println("/addBrand");
        System.out.println(brand);
        try {
            brandService.addBrand(brand);
            return RespBean.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    @GetMapping("/selectBrandById/{id}")
    @ApiOperation("根据id查询品牌")
    public Brand selectBrandById(@PathVariable Long id){
        System.out.println("/selectBrandById");
        System.out.println("id");
        Brand brand = brandService.findBrandById(id);
        return brand;
    }

    @PostMapping("/updateBrand")
    @ApiOperation("修改品牌信息")
    public RespBean updateBrand(@RequestBody @ApiParam("品牌对象") Brand brand){
        System.out.println("/updateBrand");
        System.out.println(brand);
        try {
            brandService.updateBrand(brand);
            return RespBean.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/deleteBrand")
    @ApiOperation("删除品牌信息")
    public RespBean deleteBrand(Long[] ids){
        System.out.println("/deleteBrand");
        System.out.println(ids.toString());

        try {
            brandService.deleteNews(ids);
            return RespBean.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @GetMapping("/getBrandMap")
    @ApiOperation("获取品牌数据")
    public List<Map> getBrandMap(){
        System.out.println("/getBrandMap");
        return brandService.getBrandMap();
    }


}
