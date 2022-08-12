package com.kaibutsusama.nacos.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 4:48
 */

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@ComponentScan(basePackages = {"com.kaibutsusama"})
public class NacosDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryServerApplication.class,args);
    }

    /**
     * 提供获取股票价格查询接口
     * @param name
     * @return
     */
    @RequestMapping("/getStockPrice")
    public String getStockPrice(@RequestParam(defaultValue = "我是默认值")String name){
        return "我是股票:" + name + "价格:" + new Random().nextInt(100-20) + 20;
    }
}
