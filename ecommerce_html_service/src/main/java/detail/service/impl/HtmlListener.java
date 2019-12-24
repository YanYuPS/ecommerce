package detail.service.impl;

import detail.service.GoodsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听：生成静态页
 */
@Component
public class HtmlListener implements MessageListener {

    @Autowired
    private GoodsDetailService goodsDetailService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage= (TextMessage)message;

        try {
            String text = textMessage.getText();
            System.out.println("接收到消息："+text);
            boolean b = goodsDetailService.goodsDetailHTML(Long.parseLong(text));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
