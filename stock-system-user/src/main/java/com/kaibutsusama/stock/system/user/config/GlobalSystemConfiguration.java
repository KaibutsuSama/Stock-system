package com.kaibutsusama.stock.system.user.config;

import com.kaibutsusama.stock.system.common.generator.GlobalIDGenerator;
import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/24 15:54
 */

@Configuration
public class GlobalSystemConfiguration{

    @Value("${snowflake.workerId}")
    private Long workerId;

    @Value("${snowflake.datacenterId}")
    private Long datacenterId;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    /**
     * 全局ID生成器
     * @return
     */
    @Bean
    public GlobalIDGenerator globalIDGenerator() {
        if(null == workerId || null == datacenterId) {
            return new GlobalIDGenerator();
        }
        return new GlobalIDGenerator(workerId, datacenterId);
    }

    /**
     * TokenStore的配置
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(GlobalConstants.OAUTH_PREFIX_KEY);
        return tokenStore;
    }
}
