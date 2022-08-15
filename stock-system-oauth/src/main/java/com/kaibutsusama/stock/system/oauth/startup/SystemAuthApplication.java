package com.kaibutsusama.stock.system.oauth.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:28
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kaibutsusama"})
@EntityScan(basePackages = {"com.kaibutsusama"})
@EnableJpaRepositories(basePackages = {"com.kaibutsusama"})
@EnableCaching
public class SystemAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemAuthApplication.class,args);
    }
}
