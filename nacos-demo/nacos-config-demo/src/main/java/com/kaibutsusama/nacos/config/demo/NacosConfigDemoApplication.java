package com.kaibutsusama.nacos.config.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 5:06
 */
@SpringBootApplication
@RestController
@RefreshScope
public class NacosConfigDemoApplication {

    @Value(value = "${stockName: 我是默认值}")
    private String stockName;

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigDemoApplication.class, args);
    }

    /**
     * 提供股票名称接口
     * @return
     */
    @RequestMapping("/getStockName")
    public String getStockName(){
        return "股票名称:" + stockName;
    }
}
