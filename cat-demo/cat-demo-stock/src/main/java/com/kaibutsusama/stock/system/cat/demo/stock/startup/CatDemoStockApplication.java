package com.kaibutsusama.stock.system.cat.demo.stock.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/10 0:11
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kaibutsusama"})
@RestController
public class CatDemoStockApplication {

    /**
     * 提供Stock接口
     * @return
     * @throws Exception
     */
    @RequestMapping("/stock")
    public String stock() throws Exception {
        Thread.sleep(150);
        return "stock success";
    }

    public static void main(String[] args) {
        SpringApplication.run(CatDemoStockApplication.class,args);
    }
}
