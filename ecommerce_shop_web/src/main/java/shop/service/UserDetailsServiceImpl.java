package shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pojo.TbSeller;
import goods.service.SellerService;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全登录
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl");

        //角色list
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        //商家
        TbSeller seller=sellerService.findOne(username);
        if(seller!=null){
            if (seller.getStatus().equals("1")){//审核通过
                return new User(username,seller.getPassword(),authorities);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

}
