package manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
//import detail.service.GoodsDetailService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.TbGoods;
import pojo.TbItem;
import pojogroup.Goods;
import goods.service.GoodsService;
//import search.service.ItemSearchService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Arrays;
import java.util.List;

/**
 * 商家商品 审核
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    //    @Reference
//    private ItemSearchService itemSearchService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination queueSolrDestination;//发送消息（新增商品的skulist），用于更新solr
    @Autowired
    private Destination queueSolrDeleteDestination;//发送消息（删除商品的id），用于删除solr中的信息

    @Autowired
    private Destination topicHtmlDestination;//订阅，要生成静态页的商品id
    @Autowired
    private Destination topicHtmlDeleteDestination;//订阅，要生成静态页的商品id

//    @Reference
//    private GoodsDetailService goodsDetailService;



    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加：SPU+商品扩展信息+SKU
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        goods.getGoods().setSellerId("自营");

        try {
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods) {
        //校验是否是当前商家的id
        Goods goods2 = goodsService.findOne(goods.getGoods().getId());
        //获取当前登录的商家ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //如果传递过来的商家ID并不是当前登录的用户的ID,则属于非法操作
        if(!goods2.getGoods().getSellerId().equals(sellerId) ||  !goods.getGoods().getSellerId().equals(sellerId) ){
            return new Result(false, "操作非法");
        }

        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 上架下架
     * @param goodsIds
     * @param status
     * @return
     */
    @RequestMapping("/updateMarket")
    public Result updateMarket(Long[] goodsIds, String status) {
        try {
            goodsService.updateMarket(goodsIds, status);
            return new Result(true, "更改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更改状态失败");
        }
    }




    /**
     * 显示一个
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 删除
     *
     * 发送消息（删除商品的ids）
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);

            //删除solr索引库中的信息
//            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));

            //solr
            jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });
            //html
            jmsTemplate.send(topicHtmlDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });


            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 显示列表+分页+查找
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        return goodsService.findPage(goods, page, rows);
    }

    /**
     * 查找
     *
     * @param goods
     * @return
     */
    @RequestMapping("/searchAll")
    public PageResult searchAll(@RequestBody TbGoods goods) {
        return goodsService.searchAll(goods);
    }

    /**
     * 审核
     *
     * 审核通过：发送消息（SKU list）到activemq
     *
     * @param goodsIds
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] goodsIds, String status) {
        try {
            goodsService.updateStatus(goodsIds, status);

            //审核通过：读取SKU，供搜索页使用；生成商品静态页
            if (status.equals("2")) {//审核通过
                //查询SKU，状态说明：SKU状态，0不启用，1启用
                List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(goodsIds, "1");

                if (itemList!=null && itemList.size() > 0) {
                    //调用搜索接口实现数据批量导入
//                    itemSearchService.importList(itemList);

                    //发送消息（审核通过的商品，放到消息中间件的队列里，供搜索页面使用）
                    final String jsonString = JSON.toJSONString(itemList);
                    jmsTemplate.send(queueSolrDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(jsonString);
                        }
                    });
                } else {
                    System.out.println("商品"+goodsIds+"没有SKU明细数据");
                }

                //静态页生成
                for (final Long goodsId : goodsIds) {
//                    goodsDetailService.goodsDetailHTML(goodsId);

                    //发送消息
                    jmsTemplate.send(topicHtmlDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(goodsId+"");
                        }
                    });
                }
            }

            return new Result(true, "更改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更改状态失败");
        }
    }


    /**
     * 生成静态页
     *
     * @param goodsId 商品id
     */
    @RequestMapping("/goodsDetailHTML")
    public void goodsDetailHTML(Long goodsId) {
//        goodsDetailService.goodsDetailHTML(goodsId);
        //发送消息
        jmsTemplate.send(topicHtmlDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(goodsId+"");
            }
        });
    }

}
