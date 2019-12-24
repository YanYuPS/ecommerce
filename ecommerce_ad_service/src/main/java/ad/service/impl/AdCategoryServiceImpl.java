package ad.service.impl;

import ad.service.AdCategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import mapper.TbContentCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pojo.TbContentCategory;
import pojo.TbContentCategoryExample;

import java.util.List;

@Service
@Transactional
public class AdCategoryServiceImpl implements AdCategoryService {

    @Autowired
    private TbContentCategoryMapper adCategoryMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbContentCategory> findAll() {
        return adCategoryMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContentCategory> page=   (Page<TbContentCategory>) adCategoryMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContentCategory contentCategory) {
        adCategoryMapper.insert(contentCategory);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContentCategory contentCategory){
        adCategoryMapper.updateByPrimaryKey(contentCategory);
    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public TbContentCategory findOne(Long id){
        return adCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            adCategoryMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContentCategory contentCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();

        if(contentCategory!=null){
            if(contentCategory.getName()!=null && contentCategory.getName().length()>0){
                criteria.andNameLike("%"+contentCategory.getName()+"%");
            }

        }

        Page<TbContentCategory> page= (Page<TbContentCategory>)adCategoryMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
