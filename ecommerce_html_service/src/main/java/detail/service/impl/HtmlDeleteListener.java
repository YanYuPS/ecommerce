package detail.service.impl;

import detail.service.GoodsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

@Component
public class HtmlDeleteListener implements MessageListener {
    @Autowired
    private GoodsDetailService goodsDetailService;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage= (ObjectMessage) message;
        try {
            Long[] goodsIds = (Long[]) objectMessage.getObject();

            boolean b = goodsDetailService.goodsDetailHTMLDelete(goodsIds);
            System.out.println("网页删除结果："+b);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
