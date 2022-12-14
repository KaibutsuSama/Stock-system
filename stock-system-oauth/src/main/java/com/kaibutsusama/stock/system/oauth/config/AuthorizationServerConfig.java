package com.kaibutsusama.stock.system.oauth.config;

import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.oauth.bo.OAuthTradeUser;
import com.kaibutsusama.stock.system.oauth.service.AuthClientDetailService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:36
 */
@Configuration
@EnableAuthorizationServer
@Log4j2
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * ???????????????
     */
    @Autowired
    private DataSource dataSource;
    /**
     * ????????????????????????????????????
     */
    @Autowired
    private UserDetailsService authStockUserDetailService;

    /**
     * ?????????????????????
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * Redis????????????
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * t_oauth_client_details ????????????????????????client_id???client_secret
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService ????????????
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from t_oauth_client_details";

    /**
     * ?????????????????????
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * ?????????client_id ??????
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * Redis ????????????
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> stockRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key??????????????? ??????String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // hashkey??????????????? ??????String
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // value????????????????????? ??????JDK???????????????
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        // hashvalue????????????????????? ??????JDK???????????????
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        // ??????Redis?????????????????????
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


    /**
     * ?????????Client?????????????????????????????? ?????????
     * @param clients
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        AuthClientDetailService clientDetailsService = new AuthClientDetailService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }


    /**
     * ????????????token?????????401??????
     * @param oauthServer
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }


    /**
     * ??????????????????
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .userDetailsService(authStockUserDetailService)
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false);
    }

    /**
     * TokenStore??????????????? ??????Redis??????
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(GlobalConstants.OAUTH_PREFIX_KEY);
        tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return super.extractKey(authentication);
            }
        });
        return tokenStore;
    }

    /**
     * token??????????????? ??????????????????
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            try {
                if (GlobalConstants.OAUTH_CLIENT_CREDENTIALS
                        .equals(authentication.getOAuth2Request().getGrantType())) {
                    return accessToken;
                }
                // ??????MAP ?????????????????????
                final Map<String, Object> additionalInfo = new HashMap<>(16);
                OAuthTradeUser authTradeUser = (OAuthTradeUser) authentication.getUserAuthentication().getPrincipal();
                if (null != authTradeUser) {
                    TradeUser tradeUser = authTradeUser.getTradeUser();
                    // ???????????????????????????????????????
                    additionalInfo.put(GlobalConstants.OAUTH_DETAILS_USER_ID, tradeUser.getId());
                    additionalInfo.put(GlobalConstants.OAUTH_DETAILS_USERNAME, tradeUser.getName());
                    additionalInfo.put(GlobalConstants.OAUTH_DETAILS_LOGIN_INFO, tradeUser.getEmail() + "|" + tradeUser.getAddress());
                    additionalInfo.put("active", true);
                }
                // ????????????????????????????????? ???????????????TOKEN
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            return accessToken;
        };
    }
}
