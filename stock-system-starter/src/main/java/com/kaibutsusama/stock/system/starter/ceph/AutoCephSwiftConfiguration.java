package com.kaibutsusama.stock.system.starter.ceph;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/24 15:40
 */
@Configuration
@EnableAutoConfiguration
@ConditionalOnProperty(name = "ceph.authUrl")
public class AutoCephSwiftConfiguration {

    @Value("${ceph.username}")
    private String username;
    @Value("${ceph.password}")
    private String password;
    @Value("${ceph.authUrl}")
    private String authUrl;
    @Value("${ceph.defaultContainerName}")
    private String defaultContainerName;


    @Bean
    public CephSwiftOperator cephSwiftOperator() {
        return new CephSwiftOperator(username, password, authUrl, defaultContainerName);
    }

}