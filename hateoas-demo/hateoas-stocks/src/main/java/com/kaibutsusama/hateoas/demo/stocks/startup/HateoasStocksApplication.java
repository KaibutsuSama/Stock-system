package com.kaibutsusama.hateoas.demo.stocks.startup;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 8:56
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.kaibutsusama"})
@EntityScan(basePackages = {"com.kaibutsusama"})
@EnableJpaRepositories(basePackages = {"com.kaibutsusama"})
public class HateoasStocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(HateoasStocksApplication.class,args);
    }

    /**
     * Hibernate初始化
     * @return
     */
    @Bean
    public Hibernate5Module hibernate5Module(){
        return new Hibernate5Module();
    }

    /**
     * 用于Json数据封装处理
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> {
            builder.indentOutput(true);
        };
    }
}
