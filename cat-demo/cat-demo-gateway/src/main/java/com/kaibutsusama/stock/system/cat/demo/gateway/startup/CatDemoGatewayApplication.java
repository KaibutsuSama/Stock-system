package com.kaibutsusama.stock.system.cat.demo.gateway.startup;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Transaction;
import com.kaibutsusama.stock.system.cat.demo.gateway.catutils.CatRestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/9 21:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kaibutsusama"})
@RestController
public class CatDemoGatewayApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service2.address:localhost:8082}")
    private String serviceAddress;

    public static void main(String[] args) {
        SpringApplication.run(CatDemoGatewayApplication.class,args);
    }

    /**
     * Gateway Test
     * @return
     * @throws Exception
     */
    @RequestMapping("/gateway")
    public String gateway() throws Exception {
        Thread.sleep(100);
        String response = restTemplate.getForObject("http://" + serviceAddress + "/order", String.class);
        return "gateway response =>" + response;
    }

    /**
     * Test error
     * @return
     * @throws Exception
     */
    @RequestMapping("/timeout")
    public String timeout() throws Exception {
        Transaction t = Cat.newTransaction(CatConstants.TYPE_URL, "timeout");
        try {
            Thread.sleep(100);
            String response = restTemplate.getForObject("http://" + serviceAddress + "timeout", String.class);
            return response;
        } catch (Exception e) {
            Cat.getProducer().logError(e);
            t.setStatus(e);
            throw e;
        } finally {
            t.complete();
        }
    }

    /**
     * 初始化RestTemplate
     * @return
     */
    @Bean
    RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();

        //保存并传递调用链的Context
        restTemplate.setInterceptors(Collections.singletonList(new CatRestInterceptor()));

        return restTemplate;
    }
}
