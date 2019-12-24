package search.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.TbItem;
import search.service.ItemSearchService;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

/**
 * 监听增加的商品skulist
 */

@Component
public class ItemSearchListener implements MessageListener {
    @Autowired
    private ItemSearchService itemSearchService;


    /**
     * 监听消息
     * 将消息（SKU list）导入solr
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        System.out.println("监听接收到消息...");
        try {
            //消息内容
            TextMessage textMessage=(TextMessage)message;
            String text = textMessage.getText();

            //处理接收到的SKU
            List<TbItem> list = JSON.parseArray(text,TbItem.class);
            for(TbItem item:list){
                System.out.println(item.getId()+" "+item.getTitle());

                Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
                item.setSpecMap(specMap);//给带注解的字段赋值

                //销量
                item.setSaleNum(item.getNum()-item.getStockCount());
            }

            itemSearchService.importList(list);//导入solr
            System.out.println("成功导入到索引库");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
