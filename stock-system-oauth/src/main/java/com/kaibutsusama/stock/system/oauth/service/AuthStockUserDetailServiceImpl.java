package com.kaibutsusama.stock.system.oauth.service;

import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.oauth.bo.OAuthTradeUser;
import com.kaibutsusama.stock.system.oauth.repository.TradeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:39
 */
@Service("authStockUserDetailService")
public class AuthStockUserDetailServiceImpl implements UserDetailsService {

    /**
     * 用户的数据层接口
     */
    @Autowired
    private TradeUserRepository tradeUserRepository;

    /**
     * 缓存管理接口
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * 根据用户账号获取用户对象接口
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userNo) throws UsernameNotFoundException {
        // 1. 从缓存中查找用户对象
        Cache cache = cacheManager.getCache(GlobalConstants.OAUTH_KEY_STOCK_USER_DETAILS);
        if(null != cache && null != cache.get(userNo)) {
            return (UserDetails)cache.get(userNo).get();
        }

        // 2. 如果缓存未找到， 查询数据库
        TradeUser tradeUser = tradeUserRepository.findByUserNo(userNo);
        if(null == tradeUser) {
            throw new UsernameNotFoundException(userNo + " not valid! ");
        }

        // 3. 对用户信息做封装处理
        UserDetails userDetails = new OAuthTradeUser(tradeUser);

        // 4. 将封装的用户信息放入到缓存当中
        cache.put(userNo, userDetails);
        return userDetails;
    }
}