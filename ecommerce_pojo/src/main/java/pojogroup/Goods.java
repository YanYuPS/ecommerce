package pojogroup;

import pojo.TbGoods;
import pojo.TbGoodsDesc;
import pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * 商品
 */
public class Goods implements Serializable {
    private TbGoods goods;//商品基本信息SPU
    private TbGoodsDesc goodsDesc;//商品信息扩展
    private List<TbItem> itemList;//商品销售信息SKU

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
