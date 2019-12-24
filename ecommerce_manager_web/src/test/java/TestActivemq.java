import com.alibaba.fastjson.JSON;
import entity.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.TbItem;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring*.xml")
public class TestActivemq {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination topicHtmlDestination;//订阅，要生成静态页的商品id
//    @Autowired
//    private Destination topicHtmlDeleteDestination;//订阅，要生成静态页的商品id

    @Test
    public void html(){
        Long goodsId=149187842867972L;
        jmsTemplate.send(topicHtmlDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(goodsId+"");
            }
        });
    }



}
