package manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import goods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import entity.PageResult;
import entity.Result;
import pojo.TbBrand;
import goods.service.BrandService;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;
    @Reference
    private TypeTemplateService typeTemplateService;


    /**
     * 全部 品牌列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    /**
     * 当前页 品牌列表
     * @param page 页码
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page,int rows){
        return brandService.findPage(page, rows);
    }

    /**
     * 添加一条 品牌数据
     * @param brand
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            return new Result(true,"增加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"增加失败");
        }

    }

    /**
     * 修改一条 品牌数据
     * @param brand
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);

            //更新模版
            typeTemplateService.updateBrandId(brand);

            return new Result(true,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /**
     * 获得一条数据
     * @param id
     * @return
     */
    @RequestMapping("findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
    }

    /**
     * 删除 品牌数据
     * @param ids
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }

    /**
     * 条件查询
     * @param brand
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, int page,int rows){
        return brandService.findPage(brand,page, rows);
    }

    /**
     *
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return brandService.selectOptionList();
    }

}
