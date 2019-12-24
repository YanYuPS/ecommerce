package goods.service;
import java.util.List;
import java.util.Map;

import pojo.TbBrand;
import pojo.TbSpecification;
import pojo.TbTypeTemplate;

import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface TypeTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize);


    List<Map> selectOptionList();

	/**
	 * 根据类型模版id，获得类型模版和规格列表
	 * @param id
	 * @return
	 */
	List<Map> findSpecList(Long id);

	/**
	 * 根据品牌id 查询含有这个品牌id的模版list,并更新list的brand，并更新redis
	 * @return
	 */
	void updateBrandId(TbBrand brand);
	/**
	 * 更新规格
	 * @return
	 */
	void updateSpecId(TbSpecification specification);

}
