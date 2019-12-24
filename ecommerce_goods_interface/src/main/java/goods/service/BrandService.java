package goods.service;

import entity.PageResult;
import pojo.TbBrand;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 */
public interface BrandService {
    /**
     * 全部 品牌列表
     * @return list
     */
    List<TbBrand> findAll();

    /**
     * 当前页 品牌列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 总数，当前页列表
     */
    PageResult findPage(int pageNum,int pageSize);

    /**
     * 添加一条 品牌数据
     * @param brand
     */
    void add(TbBrand brand);

    /**
     * 修改一条 品牌数据
     * @param brand
     */
    void update(TbBrand brand);

    /**
     * 根据id获得一条数据
     * @param id
     * @return
     */
    TbBrand findOne(Long id);

    /**
     * 删除 品牌数据
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 条件查询
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findPage(TbBrand brand, int pageNum,int pageSize);

    /**
     * 返回下拉列表数据
     * @return
     */
    public List<Map> selectOptionList();
}
