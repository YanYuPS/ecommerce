package shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import pojo.TbTypeTemplate;
import goods.service.TypeTemplateService;

import entity.PageResult;
import entity.Result;

/**
 * 品牌列表
 */

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbTypeTemplate findOne(Long id){
        return typeTemplateService.findOne(id);
    }

    /**
     * 根据类型模版id，获得类型模版和规格列表
     * @param id
     * @return
     */
    @RequestMapping("/findSpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }

}
