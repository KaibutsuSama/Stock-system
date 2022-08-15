package com.kaibutsusama.stock.system.oauth.service;

import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:39
 */
public class AuthClientDetailService extends JdbcClientDetailsService {

    public AuthClientDetailService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 重写原生方法支持redis缓存
     *
     * @param clientId
     * @return
     * @throws InvalidClientException
     */
    @Override
    @Cacheable(value = GlobalConstants.OAUTH_KEY_CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }
}
