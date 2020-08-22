package com.zhc.mymall.controller;

import com.zhc.mymall.pojo.ItemCat;
import com.zhc.mymall.pojo.RespBean;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @GetMapping("/findOneByItemCat")
    public ItemCat findOneByItemCat(Long id){
        System.out.println("findOneByItemCat");
        return itemCatService.findOneByItemCat(id);
    }

    @GetMapping("/findItemCatByParentId")
    public List<ItemCat> findItemCatByParentId(Long parentId){
        System.out.println("findItemCatByParentId");
        return itemCatService.findByParentId(parentId);
    }

    @GetMapping("/findAllByItemCat")
    public List<ItemCat> findAllByItemCat(){
        System.out.println("findAllByItemCat");
        return itemCatService.findAllByItemCat();
    }

    /**
     * 返回全部列表
     * @return
     */
    @GetMapping("/findPageByItemCat")
    public ResultPage findPageByItemCat(int pageNum, int pageSize){
        System.out.println("findPageByItemCat");
        return itemCatService.findPageByItemCat(pageNum, pageSize);
    }

    /**
     * 增加
     * @param itemCat
     * @return
     */
    @PostMapping("/addItemCat")
    public RespBean add(@RequestBody ItemCat itemCat){
        System.out.println("/addItemCat");
        try {
            itemCatService.addItemCat(itemCat);
            return RespBean.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    /**
     * 修改
     * @param itemCat
     * @return
     */
    @PostMapping("/updateItemCat")
    public RespBean update(@RequestBody ItemCat itemCat){
        try {
            itemCatService.updateItemCat(itemCat);
            return RespBean.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("修改失败");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @GetMapping("/deleteItemCats")
    public RespBean deleteItemCats(Long [] ids){
        try {
            itemCatService.deleteItemCats(ids);
            return RespBean.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @RequestMapping("/searchByItemCat")
    public ResultPage search(@RequestBody ItemCat itemCat, int pageNum, int pageSize  ){
        return itemCatService.searchByItemCat(itemCat, pageNum, pageSize);
    }

}
