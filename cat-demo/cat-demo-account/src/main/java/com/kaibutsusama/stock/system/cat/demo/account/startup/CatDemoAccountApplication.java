package com.kaibutsusama.stock.system.cat.demo.account.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/9 23:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kaibutsusama"})
@RestController
public class CatDemoAccountApplication {

    /**
     * 提供Account接口
     * @return
     * @throws Exception
     */
    @RequestMapping("/account")
    public String account() throws Exception {
        Thread.sleep(150);
        return "account success";
    }

    public static void main(String[] args) {
        SpringApplication.run(CatDemoAccountApplication.class,args);
    }
}
