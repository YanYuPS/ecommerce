package detail.service;

/**
 * 商品详情页
 */
public interface GoodsDetailService {
    /**
     * 生成商品详细页
     * @param goodsId
     */
    public boolean goodsDetailHTML(Long goodsId);

    /**
     * 删除详情页
     * @param goodsIds
     * @return
     */
    boolean goodsDetailHTMLDelete(Long[] goodsIds);

}
