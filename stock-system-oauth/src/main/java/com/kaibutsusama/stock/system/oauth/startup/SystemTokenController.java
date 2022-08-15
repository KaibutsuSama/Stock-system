package com.kaibutsusama.stock.system.oauth.startup;

import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 18:32
 */
@RestController
@RequestMapping("/token")
@Log4j2
public class SystemTokenController {

    private static final String STOCK_OAUTH_ACCESS = GlobalConstants.OAUTH_PREFIX_KEY;

    @Autowired
    private RedisTemplate stockRedisTemplate;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @RequestMapping("/login")
    public ModelAndView require() {
        log.info("token login login");
        return new ModelAndView("ftl/login");
    }


    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @RequestMapping("/success")
    public String success() {
        log.info("token login success!");
        return "login success";
    }


    /**
     * 退出token
     *
     * @param authHeader Authorization
     */
    @DeleteMapping("/logout")
    public String logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StringUtils.isEmpty(authHeader)) {
            return "退出失败，token 为空";
        }

        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, "").trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StringUtils.isEmpty(accessToken.getValue())) {
            return "退出失败，token 无效";
        }

        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
        cacheManager.getCache(GlobalConstants.OAUTH_KEY_STOCK_USER_DETAILS).evict(auth2Authentication.getName());
        tokenStore.removeAccessToken(accessToken);
        return "退出成功， token 已清除";
    }

    /**
     * 令牌管理调用
     *
     * @param token token
     * @return
     */
    @DeleteMapping("/{token}")
    public String delToken(@PathVariable("token") String token) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return "token 已清除";
    }
}
