package enter.controller;

import ad.service.AdService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.TbContent;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {

    @Reference
    private AdService adService;

    /**
     * 根据广告分类ID查询广告列表
     * @param categoryId
     * @return
     */
    @RequestMapping("/findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId) {
        return adService.findByCategoryId(categoryId);
    }



}
