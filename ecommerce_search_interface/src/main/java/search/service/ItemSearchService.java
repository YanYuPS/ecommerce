package search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    /**
     * 全部
     * @return
     */
    public Map<String,Object> searchAll();

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    public Map<String,Object> search(Map searchMap);

    /**
     * 分类list 导入 solr索引库
     * @param list
     */
    public void importList(List list);

    /**
     * 删除solr数据
     * @param goodsIdList
     */
    public void deleteByGoodsIds(List goodsIdList);

}
