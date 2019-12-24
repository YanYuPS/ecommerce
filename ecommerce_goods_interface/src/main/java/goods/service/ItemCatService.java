package goods.service;

import java.util.List;

import pojo.TbItemCat;

import entity.PageResult;

/**
 * 服务层接口
 */
public interface ItemCatService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbItemCat> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(TbItemCat itemCat);


    /**
     * 修改
     */
    public void update(TbItemCat itemCat);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbItemCat findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize);


    /**
     * 根据上级ID，返回全部下级列表
     *
     * @param parentId
     * @return
     */
    public List<TbItemCat> findByParentId(Long parentId);

    /**
     * 根据上级ID，返回当前页下级列表
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findByParentId(Long parentId,int pageNum, int pageSize);

    /**
     * 查询下级+分页
     * @param itemCat
     * @param parentId
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPageByParentId(TbItemCat itemCat, Long parentId, int pageNum, int pageSize);

}
