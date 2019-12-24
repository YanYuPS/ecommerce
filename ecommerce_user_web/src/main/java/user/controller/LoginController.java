package user.controller;

import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 获取登录名
     * @return
     */
    @RequestMapping("/name")
    public Result showName() {
        //得到登陆人账号,判断当前是否有人登陆 (当用户未登陆时，username的值为anonymousUser)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(username.equals("anonymousUser")) {
            return new Result(false,"请登录");
        }else{
            return new Result(true,username);
        }
    }
}

