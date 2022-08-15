package com.kaibutsusama.stock.system.oauth.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 18:33
 */
@RestController
@RequestMapping("/trade")
public class SystemUserController {

    @Autowired
    private UserDetailsService authStockUserDetailService;

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    public UserDetails getUser(@RequestParam("username")String username) {

        UserDetails userDetails = authStockUserDetailService.loadUserByUsername(username);

        return userDetails;
    }
}