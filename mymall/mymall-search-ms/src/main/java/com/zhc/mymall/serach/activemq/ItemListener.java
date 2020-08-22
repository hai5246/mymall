package com.zhc.mymall.serach.activemq;

import com.alibaba.fastjson.JSON;
import com.zhc.mymall.pojo.Item;
import com.zhc.mymall.serach.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.util.Arrays;
import java.util.List;

@Component
public class ItemListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @JmsListener(destination = JmsConfig.TOPIC_SOLR, containerFactory = "jmsListenerContainerTopic")
    public void onItemSearch(Message message){
        TextMessage textMessage = (TextMessage) message;

        try{
            String text = textMessage.getText();//JOSN字符串
            System.out.println("监听到信息:" + text);

            List<Item> itemList = JSON.parseArray(text, Item.class);
            itemSearchService.importList(itemList);
            System.out.println("导入到solr库");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @JmsListener(destination = JmsConfig.QUEUE_DELETE, containerFactory = "jmsListenerContainerQueue")
    public void onItemDelete(Message message) {
        ObjectMessage objectMessage =(ObjectMessage)message;
        try {
            Long[] goodsIds= (Long[]) objectMessage.getObject();
            System.out.println("监听获取到消息："+goodsIds);
            itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
            System.out.println("执行索引库删除");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
