package com.kaibutsusama.stock.system.oauth.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:36
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    /**
     * 用户服务接口
     */
    @Autowired
    private UserDetailsService authStockUserDetailService;

    /**
     * 密码编码器（支持加密处理）
     */
    @Autowired
    private StockPasswordEncoder stockPasswordEncoder;


    /**
     * Web服务认证配置
     * @param http
     */
    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .formLogin()
                .loginPage("/token/login")
                .loginProcessingUrl("/token/form")
                .defaultSuccessUrl("/token/success")
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/token/**",
                        "/actuator/**",
                        "/druid/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    /**
     * 不拦截静态资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static.css/**");
    }

    /**
     * 认证管理器
     * @return
     */
    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authStockUserDetailService).passwordEncoder(stockPasswordEncoder);
    }
}
