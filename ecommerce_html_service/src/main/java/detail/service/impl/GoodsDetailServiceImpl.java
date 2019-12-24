package detail.service.impl;


//import com.alibaba.dubbo.config.annotation.Service;
import detail.service.GoodsDetailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import mapper.TbGoodsDescMapper;
import mapper.TbGoodsMapper;
import mapper.TbItemCatMapper;
import mapper.TbItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import pojo.TbGoods;
import pojo.TbGoodsDesc;
import pojo.TbItem;
import pojo.TbItemExample;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情页
 */
@Service
@Transactional
public class GoodsDetailServiceImpl implements GoodsDetailService {

    //html本地生成目录
//    @Value("http://localhost:9105/ecommerce_html_web/admin/")
//    @Value("/Users/rainbow/Desktop/ecommerceFTL/goods/")
    @Value("/Users/rainbow/workspace/maven/ecommerce/ecommerce_html_web/src/main/webapp/admin/")
    private String detailDir;

    //加载ftl
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    //SPU
    @Autowired
    private TbGoodsMapper goodsMapper;

    //扩展信息
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    //分类
    @Autowired
    private TbItemCatMapper itemCatMapper;

    //SKU
    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 生成商品详情页
     * 根据goods.ftl生成goodsId.html
     * @param goodsId
     * @return
     */
    //问题：内容为空时页面显示
    @Override
    public boolean goodsDetailHTML(Long goodsId){
        try {
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("goods.ftl");

            Map dataModel=new HashMap<>();

            //根据商品id，获得商品信息spu
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", goods);

            //根据商品id，获得商品扩展信息
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", goodsDesc);

            //商品分类（3级）

            String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1", itemCat1);
            dataModel.put("itemCat2", itemCat2);
            dataModel.put("itemCat3", itemCat3);

            //4.SKU列表
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo("1");//状态为有效
            criteria.andGoodsIdEqualTo(goodsId);//ID
            example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认
            List<TbItem> itemList = itemMapper.selectByExample(example);
            dataModel.put("itemList", itemList);

            Writer out=new FileWriter(detailDir+goodsId+".html");

            template.process(dataModel, out);

            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除商品详情页
     * @param goodsIds
     * @return
     */
    @Override
    public boolean goodsDetailHTMLDelete(Long[] goodsIds){
        try {
            for(Long goodsId:goodsIds){
                new File(detailDir+goodsId+".html").delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
