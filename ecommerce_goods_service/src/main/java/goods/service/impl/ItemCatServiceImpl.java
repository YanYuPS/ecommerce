package goods.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import mapper.TbItemCatMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import pojo.TbItemCat;
import pojo.TbItemCatExample;
import pojo.TbItemCatExample.Criteria;
import goods.service.ItemCatService;

import entity.PageResult;

/**
 * 分类
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    //用于缓存
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbItemCat> findAll() {
        return itemCatMapper.selectByExample(null);
    }


    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbItemCat itemCat) {
        itemCatMapper.insert(itemCat);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbItemCat itemCat) {
        itemCatMapper.updateByPrimaryKey(itemCat);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbItemCat findOne(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            itemCatMapper.deleteByPrimaryKey(id);
        }

        List<Long> idList= new ArrayList<Long>(Arrays.asList(ids));

        //查询 下下级
        TbItemCatExample exampleLast=new TbItemCatExample();
        Criteria criteriaLast=exampleLast.createCriteria();
        criteriaLast.andParentIdIn(idList);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(exampleLast);

        //添加idList
        if(tbItemCats!=null){
            for(TbItemCat tbItemCat : tbItemCats){
                idList.add(tbItemCat.getId());
            }
        }

        //删除
        TbItemCatExample example=new TbItemCatExample();
        Criteria criteria=example.createCriteria();
        criteria.andParentIdIn(idList);
        itemCatMapper.deleteByExample(example);

    }

    @Override
    public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();

        if (itemCat != null) {
            if (itemCat.getName() != null && itemCat.getName().length() > 0) {
                criteria.andNameLike("%" + itemCat.getName() + "%");
            }

        }

        Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }


    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        return itemCatMapper.selectByExample(example);
    }

    @Override
    public PageResult findByParentId(Long parentId, int pageNum, int pageSize) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        PageHelper.startPage(pageNum,pageSize);
        Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public PageResult findPageByParentId(TbItemCat itemCat, Long parentId, int pageNum, int pageSize) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        if (itemCat != null) {
            if (itemCat.getName() != null && itemCat.getName().length() > 0) {
                criteria.andNameLike("%" + itemCat.getName() + "%");
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(example);

        //加入缓存
        //每次执行查询的时候，一次性读取缓存进行存储 (因为每次增删改都要执行此方法)
        List<TbItemCat> list = findAll();
        for(TbItemCat tbItemCat:list){
            redisTemplate.boundHashOps("itemCat").put(tbItemCat.getName(), tbItemCat.getTypeId());
        }

        return new PageResult(page.getTotal(), page.getResult());
    }

}
