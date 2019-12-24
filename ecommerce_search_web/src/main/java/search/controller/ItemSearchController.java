package search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import search.service.ItemSearchService;

import java.util.Map;

@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {
    @Reference
    private ItemSearchService itemSearchService;

    /**
     * 全部
     * @return
     */
    @RequestMapping("/searchAll")
    public Map<String, Object> searchAll(){
        return  itemSearchService.searchAll();
    }

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @RequestMapping("/search")
    public Map<String, Object> search(@RequestBody Map searchMap ){
        return  itemSearchService.search(searchMap);
    }
}

