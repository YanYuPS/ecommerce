package goods.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import mapper.TbSpecificationOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import mapper.TbTypeTemplateMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import pojo.*;
import pojo.TbTypeTemplateExample.Criteria;
import goods.service.TypeTemplateService;

import entity.PageResult;

/**
 * 模版---品牌+规格
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    //用于缓存
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbTypeTemplateMapper typeTemplateMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 将数据存入缓存
     */
    private void saveToRedis(){
        //获取模板数据
        List<TbTypeTemplate> typeTemplateList = findAll();
        //循环模板
        for(TbTypeTemplate typeTemplate :typeTemplateList){
            //存储品牌列表
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(), brandList);
            //存储规格列表
            List<Map> specList = findSpecList(typeTemplate.getId());//根据模板ID查询规格列表
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(), specList);
        }
    }


    /**
     * 查询全部
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbTypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbTypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbTypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbTypeTemplateExample example = new TbTypeTemplateExample();
        Criteria criteria = example.createCriteria();

        if (typeTemplate != null) {
            if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
                criteria.andNameLike("%" + typeTemplate.getName() + "%");
            }
            if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
                criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
            }
            if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
                criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
            }
            if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
                criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
            }

        }

        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(example);

        //存入数据到缓存
        saveToRedis();

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return typeTemplateMapper.selectOptionList();
    }

    /**
     *
     * @param id 模版id
     * @return
     */
    @Override
    public List<Map> findSpecList(Long id) {
        //根据模版ID查模板
        TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);

        //得到规格list（id,text）
        List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);//规格

        //得到规格选项
        for(Map map:list){
            TbSpecificationOptionExample example=new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo( new Long( (Integer)map.get("id") ) );
            //查询规格表，得到规格选项list
            List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
            map.put("options", options);
        }

        return list;
    }


    /**
     * 根据品牌id 查询含有这个品牌id的模版list,并更新list的brand，并更新redis
     * @return
     */
    @Override
    public void updateBrandId(TbBrand brand) {

        //查询含有brandId的list
        TbTypeTemplateExample example = new TbTypeTemplateExample();
        Criteria criteria = example.createCriteria();
        criteria.andBrandIdsLike("%" + brand.getId() + "%");

        List<TbTypeTemplate> tbTypeTemplates = typeTemplateMapper.selectByExample(example);
        if(tbTypeTemplates==null){
            return ;
        }

        //更新list
        for(TbTypeTemplate typeTemplate:tbTypeTemplates){
            //替换品牌
            String brandIds = typeTemplate.getBrandIds();//例子：[{"id":36,"text":"大小"}]
           //+或*后跟？表示非贪婪匹配，即尽可能少的匹配
            String reg="\\{\"id\":" + brand.getId() + ",\"text\":\"(.+?)\"\\}";
            brandIds=brandIds.replaceAll(reg,"{\"id\":"+ brand.getId() +",\"text\":\""+ brand.getName() +"\"}");
            System.out.println(brandIds);

            typeTemplate.setBrandIds(brandIds);

            //更新模版
            update(typeTemplate);

            //更新缓存---存储品牌列表
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(), brandList);
        }

    }

    /**
     * 更新规格
     * @return
     */
    @Override
    public void updateSpecId(TbSpecification specification) {

        //查询含有brandId的list
        TbTypeTemplateExample example = new TbTypeTemplateExample();
        Criteria criteria = example.createCriteria();
        criteria.andSpecIdsLike("%" + specification.getId() + "%");

        List<TbTypeTemplate> tbTypeTemplates = typeTemplateMapper.selectByExample(example);
        if(tbTypeTemplates==null){
            return ;
        }

        //更新list
        for(TbTypeTemplate typeTemplate:tbTypeTemplates){
            //替换品牌
            String ids = typeTemplate.getSpecIds();//例子：[{"id":36,"text":"大小"}]
            //+或*后跟？表示非贪婪匹配，即尽可能少的匹配
            String reg="\\{\"id\":" + specification.getId() + ",\"text\":\"(.+?)\"\\}";
            ids=ids.replaceAll(reg,"{\"id\":"+ specification.getId() +",\"text\":\""+ specification.getSpecName() +"\"}");
            System.out.println(ids);

            typeTemplate.setSpecIds(ids);

            //更新模版
            update(typeTemplate);

            //更新缓存---存储品牌列表
            List<Map> specList = findSpecList(typeTemplate.getId());//根据模板ID查询规格列表
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(), specList);
        }

    }

}
