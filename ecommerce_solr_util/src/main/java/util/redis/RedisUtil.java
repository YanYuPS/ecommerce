package util.redis;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import entity.PageResult;
import mapper.TbItemMapper;
import mapper.TbSellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pojo.*;
import pojogroup.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TbItemMapper itemMapper;


    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        RedisUtil redisUtil=  (RedisUtil) context.getBean("redisUtil");

        //广告redis
//        Long id=3L;//广告类型id
//        redisUtil.findAdList(id);//查找
//        redisUtil.deleteAd(id);//删除

        //购物车redis
//        String name="zy1";//用户名
//        Long itemId=1369296L;//1369295L
////        redisUtil.deleteCart(name);
//        redisUtil.addCart(name,itemId);
//        redisUtil.findCartListFromRedis(name);

        //模版redis
        Long id=40L;
        redisUtil.findTemp(id);
    }



    //查找广告
    public List<TbContent> findAdList(Long id){
        List<TbContent> list =  (List<TbContent>)redisTemplate.boundHashOps("content").get(id);
        System.out.println(list);
        return list;
    }
    //删除广告
    public void deleteAd(Long id){
        redisTemplate.boundHashOps("content").delete(id);
    }


    //删除购物车
    public void deleteCart(String username){
        redisTemplate.boundHashOps("cartList").delete(username);
    }
    //查找购物车
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if(cartList==null){
            cartList=new ArrayList();
        }
        System.out.println(cartList);
        return cartList;
    }
    //添加购物车
    public void addCart(String username,Long itemId){
        saveCartListToRedis(username,addGoodsToCartList(findCartListFromRedis(username),itemId,3));
    }

    //添加到cartList
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {

        //根据SKU ID查询SKU信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!item.getStatus().equals("1")) {
            throw new RuntimeException("商品状态无效");
        }

        //获取商家ID
        String sellerId = item.getSellerId();

        //根据商家ID判断购物车列表中是否存在该商家的购物车
        Cart cart = searchCartBySellerId(cartList, sellerId);

        //如果购物车列表中不存在该商家的购物车
        if (cart == null) {
            //新建购物车对象 ，
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());
            TbOrderItem orderItem = createOrderItem(item, num);
            List orderItemList = new ArrayList();
            orderItemList.add(orderItem);
            cart.setOrderItemList(orderItemList);
            //将购物车对象添加到购物车列表
            cartList.add(cart);
        } else { //如果购物车列表中存在该商家的购物车
            // 判断购物车明细列表中是否存在该商品
            TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);

            if (orderItem == null) {
                //如果没有，新增购物车明细
                orderItem = createOrderItem(item, num);
                cart.getOrderItemList().add(orderItem);
            } else {
                //如果有，在原购物车明细上添加数量，更改金额
                orderItem.setNum(orderItem.getNum() + num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getNum() * orderItem.getPrice().doubleValue()));
                //如果数量操作后小于等于0，则移除
                if (orderItem.getNum() <= 0) {
                    cart.getOrderItemList().remove(orderItem);//移除购物车明细
                }
                //如果移除后cart的明细数量为0，则将cart移除
                if (cart.getOrderItemList().size() == 0) {
                    cartList.remove(cart);
                }
            }
        }
        return cartList;
    }
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }
        return null;
    }
    private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {
        for (TbOrderItem orderItem : orderItemList) {
            if (orderItem.getItemId().longValue() == itemId.longValue()) {
                return orderItem;
            }
        }
        return null;
    }
    private TbOrderItem createOrderItem(TbItem item, Integer num) {
        if (num <= 0) {
            throw new RuntimeException("数量非法");
        }

        TbOrderItem orderItem = new TbOrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * num));
        return orderItem;
    }
    //添加到购物车
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username, cartList);
    }


    //查模版的brand、spec
    public void findTemp(Long id){
        List<Map> list= (List<Map>) redisTemplate.boundHashOps("brandList").get(id);
        System.out.println(list);
        list= (List<Map>) redisTemplate.boundHashOps("specList").get(id);
        System.out.println(list);
        return ;
    }





}
