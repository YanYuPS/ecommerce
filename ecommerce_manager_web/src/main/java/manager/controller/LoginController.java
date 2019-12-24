package manager.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 获取登录的用户名
     * @return
     */
    @RequestMapping("/name")
    public Map name(){
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        Map map=new HashMap();
        map.put("loginName",name);

        Date date=new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=sf.format(date);
        map.put("dateTime",dateStr);

        return map;
    }
}
