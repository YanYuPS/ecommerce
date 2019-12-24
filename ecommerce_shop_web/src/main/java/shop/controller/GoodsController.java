package shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.TbGoods;
import pojogroup.Goods;
import goods.service.GoodsService;

import java.util.List;

/**
 * 商家商品
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

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
        String sellerId=SecurityContextHolder.getContext().getAuthentication().getName();//登录名
        goods.getGoods().setSellerId(sellerId);

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
     * 显示一个-----修改引用
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 显示列表+分页+查找
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        //获取商家ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //添加查询条件
        goods.setSellerId(sellerId);

        return goodsService.findPage(goods, page, rows);
    }

    /**
     * 审核 --- 提交审核
     * @param goodsIds
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] goodsIds, String status) {
        try {
            goodsService.updateStatus(goodsIds, status);
            return new Result(true, "更改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更改状态失败");
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
}
