package util.solr;


import com.alibaba.fastjson.JSON;
import mapper.TbItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Component;

import pojo.TbItem;
import pojo.TbItemExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;


    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil=  (SolrUtil) context.getBean("solrUtil");
//        导入数据
        solrUtil.importItemData();

//        solrUtil.search("平板电视");
    }



    /**
     * 导入审核通过的商品
     */
    public void importItemData(){
        //查找审核通过的商品
        TbItemExample example=new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//已上架
        List<TbItem> itemList = itemMapper.selectByExample(example);

        System.out.println("===商品列表===");
        for(TbItem item:itemList){
            Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
            item.setSpecMap(specMap);//给带注解的字段赋值

            System.out.println(item.getTitle());
        }

        //导入
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();

        System.out.println("===结束===");
    }

    /**
     * 查找
     * @param name
     */
    public void search(String name) {

        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("item_keywords");
        criteria.is(name);

        query.addCriteria(criteria);
        query.setOffset(20);//开始索引（默认0）
        query.setRows(20);//每页记录数(默认10)

        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);

        List<TbItem> items = page.getContent();
        for(TbItem item:items){
            Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
            item.setSpecMap(specMap);//给带注解的字段赋值
            System.out.println(item.getTitle());
        }
    }

}
