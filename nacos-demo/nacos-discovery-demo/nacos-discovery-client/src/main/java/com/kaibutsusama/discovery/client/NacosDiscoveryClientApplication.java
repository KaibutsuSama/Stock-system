package com.kaibutsusama.discovery.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 4:42
 */

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@ComponentScan(basePackages = {"com.kaibutsusama"})
public class NacosDiscoveryClientApplication {

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryClientApplication.class,args);
    }

    /**
     * RestTemplate配置
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 提供客户端访问股票价格接口
     * @param name
     * @return
     */
    @RequestMapping("/clientGetStockPrice")
    public String clientGetStockPrice(@RequestParam String name){

        return restTemplate.getForObject("http://nacos-discovery-server/getStockPrice?name="+name,String.class);
    }

}
