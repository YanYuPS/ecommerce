package ad.service.impl;

import ad.service.AdService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import mapper.TbContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import pojo.TbContent;
import pojo.TbContentExample;

import java.util.List;

@Service
@Transactional
public class AdServiceImpl implements AdService {

    @Autowired
    private TbContentMapper adMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return adMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page=   (Page<TbContent>) adMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContent content) {
        adMapper.insert(content);
        //清除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent content){
        //查询原来的分组ID
        Long categoryId = adMapper.selectByPrimaryKey(content.getId()).getCategoryId();
        //清除原分组的缓存
        redisTemplate.boundHashOps("content").delete(categoryId);

        adMapper.updateByPrimaryKey(content);

        //清除现分组缓存
        if(categoryId.longValue()!=content.getCategoryId().longValue()){
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }

    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id){
        return adMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            //清除缓存
            Long categoryId = adMapper.selectByPrimaryKey(id).getCategoryId();
            redisTemplate.boundHashOps("content").delete(categoryId);

            adMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();

        if(content!=null){
            if(content.getTitle()!=null && content.getTitle().length()>0){
                criteria.andTitleLike("%"+content.getTitle()+"%");
            }
            if(content.getUrl()!=null && content.getUrl().length()>0){
                criteria.andUrlLike("%"+content.getUrl()+"%");
            }
            if(content.getPic()!=null && content.getPic().length()>0){
                criteria.andPicLike("%"+content.getPic()+"%");
            }
            if(content.getStatus()!=null && content.getStatus().length()>0){
                criteria.andStatusLike("%"+content.getStatus()+"%");
            }

        }

        Page<TbContent> page= (Page<TbContent>)adMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据分类id，获取广告list
     * @param categoryId
     * @return
     */
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {

        //获取categoryId分类的广告list
        List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);

        //没有缓存
        if(list==null){
            System.out.println("从数据库中查询数据并放入缓存 ");
            TbContentExample example=new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(categoryId);//指定条件:分类ID
            criteria.andStatusEqualTo("1");//指定条件：有效
            example.setOrderByClause("sort_order");//排序

            list = adMapper.selectByExample(example);

            redisTemplate.boundHashOps("content").put(categoryId, list);//放入缓存
        }else{
            System.out.println("从缓存中查询数据 ");
        }

        return list;
    }
}
