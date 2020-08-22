package com.zhc.mymall.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhc.mymall.activemq.JmsConfig;
import com.zhc.mymall.pojo.Goods;
import com.zhc.mymall.pojo.Item;
import com.zhc.mymall.pojo.RespBean;
import com.zhc.mymall.pojo.ResultPage;
import com.zhc.mymall.pojogroup.GoodsGroup;
import com.zhc.mymall.service.GoodsService;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/getAllGoods")
    public List<Goods> getAllGoods() {
        System.out.println("/getAllGoods");
        return goodsService.findAllGoods();
    }

    @GetMapping("/findGoodsByPage")
    public ResultPage findGoodsByPage(int pageNum, int pageSize){
        System.out.println("/findGoodsByPage");
        PageHelper.startPage(pageNum, pageSize);
        Page<Goods> page = (Page<Goods>) goodsService.findAllGoods();
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(page.getResult());
        resultPage.setTotal(page.getTotal());
        return resultPage;
    }

    @GetMapping("/findOneByGoods")
    public Goods findOneByGoods(Long id){
        return goodsService.findOneByGoods(id);
    }

    @PostMapping("/addGoods")
    public RespBean addGoods(@RequestBody GoodsGroup goodsGroup) {
        System.out.println("#######" + goodsGroup.getGoods().getGoodsName() + "#######");
        System.out.println("#######" + goodsGroup.getGoodsDesc().getItemImages());
        System.out.println("#######" + goodsGroup.getGoodsDesc().getSpecificationItems());
        System.out.println("#######" + goodsGroup.getGoodsDesc().getCustomAttributeItems());

        try {
            String sellerId = "yqtech";//暂定
            goodsGroup.getGoods().setSellerId(sellerId);
            goodsService.add(goodsGroup);
            return RespBean.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败");
        }

    }

    @GetMapping("/deleteGoods")
    public RespBean deleteGoods(Long [] ids){
        try {
            goodsService.deleteGoods(ids);
            return RespBean.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @PostMapping("/searchGoods")
    public ResultPage searchGoods(@RequestBody Goods goods, int page, int rows  ){

        //String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        String sellerId = "yqtech"; //暂时设定

        goods.setSellerId(sellerId);

        return goodsService.searchGoods(goods, page, rows);
    }



    @GetMapping("/updateStatus")
    public RespBean updateStatus(Long[] ids,String status){
        System.out.println("ids: " + ids);
        System.out.println("status: " + status);
        try {
            goodsService.updateStatus(ids, status);  //1. 更改数据库中tb_goods表中audit_status 0-->1

            if("1".equals(status)){//如果是审核通过
                //*****导入到索引库
                //得到需要导入的SKU列表    根据spu的id查询对应的sku的商品
                List<Item> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);
                //2. 导入到solr
                //itemSearchService.importList(itemList);
                final String jsonString = JSON.toJSONString(itemList);//转换为json传输

                System.out.println("Goods conroller: " + jsonString);

                Topic topicSolrDestination = new ActiveMQTopic(JmsConfig.TOPIC_SOLR);
                jmsTemplate.send(topicSolrDestination, new MessageCreator() {

                    @Override
                    public Message createMessage(Session session) throws JMSException {

                        return session.createTextMessage(jsonString);
                    }
                });

                //****3. 生成商品详细页 freemarker
                for(final Long goodsId:ids){
                    Topic topicPageDestination = new ActiveMQTopic(JmsConfig.TOPIC_HTML);
                    jmsTemplate.send(topicPageDestination, new MessageCreator() {

                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(goodsId+"");
                        }
                    });
                }

            }

            return RespBean.ok("修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("修改状态失败");
        }
    }



}
