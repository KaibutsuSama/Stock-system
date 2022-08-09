package com.kaibutsusama.stock.system.user.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/9 4:09
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kaibutsusama"})
@RestController
public class StockUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockUserApplication.class,args);
    }

    /**
     * 提供测试访问接口
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome to stock-system";
    }
}
