package com.kaibutsusama.stock.system.cat.demo.gateway.catutils;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/9 22:50
 */
@Configuration
public class CatFilterConfigure {

    /**
     * CAT 过滤器的注册配置
     * @return
     */
    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatServletFilter filter = new CatServletFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }
}