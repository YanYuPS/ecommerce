package cart.controller;

import cart.service.CartService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import common.Constant;
import common.CookieUtil;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojogroup.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    //默认超时时间1秒
    @Reference(timeout=6000)
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    /**
     * 购物车列表
     * @return
     */
    @RequestMapping("/findCartList")
    @CrossOrigin(origins="*",allowCredentials="true")
    public List<Cart> findCartList(){
        //得到登陆人账号,判断当前是否有人登陆 (当用户未登陆时，username的值为anonymousUser)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String cartListString  = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if(cartListString==null || cartListString.equals("")){
            cartListString="[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);

        if(username.equals("anonymousUser")) {
            return cartList_cookie;
        }else{
            //如果已登录,从redis中提取
            List<Cart> cartList_redis =cartService.findCartListFromRedis(Constant.USERNAME);

            if(cartList_cookie.size()>0){//如果本地存在购物车
                //合并购物车
                cartList_redis=cartService.mergeCartList(cartList_redis, cartList_cookie);
                //清除本地cookie的数据
                CookieUtil.deleteCookie(request, response, "cartList");
                //将合并后的数据存入redis
                cartService.saveCartListToRedis(username, cartList_redis);
            }

            return cartList_redis;
        }
    }

    /**
     * 添加商品到购物车
     * @param itemId SKUid
     * @param num 数量
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    @CrossOrigin(origins="*",allowCredentials="true") //跨域，allowCredentials="true"可缺省
    public Result addGoodsToCartList(Long itemId, Integer num){
        //跨域
//        response.setHeader("Access-Control-Allow-Origin", "*");//允许的url
//        response.setHeader("Access-Control-Allow-Credentials", "true");//允许发送cookie

        //得到登陆人账号,判断当前是否有人登陆
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            List<Cart> cartList =findCartList();//读取购物车列表
            //添加购物车list
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);
            //添加到购物车
            if(username.equals("anonymousUser")) {
                //如果是未登录，保存到cookie
                CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList), 3600 * 24, "UTF-8");
            }else{
                //如果是已登录，保存到redis
                cartService.saveCartListToRedis(username, cartList);
            }
            return new Result(true, "添加成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }

    }
}
