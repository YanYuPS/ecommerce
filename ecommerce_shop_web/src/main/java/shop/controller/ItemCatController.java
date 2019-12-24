package shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.TbItemCat;
import goods.service.ItemCatService;

import java.util.List;

/**
 * 商品分类
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbItemCat> findAll(){
        return itemCatService.findAll();
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbItemCat findOne(Long id){
        return itemCatService.findOne(id);
    }

    /**
     * 根据上级ID查询商品分类列表
     * @param parentId
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<TbItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

}
