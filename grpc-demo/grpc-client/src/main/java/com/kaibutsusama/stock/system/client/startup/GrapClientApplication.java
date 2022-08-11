package com.kaibutsusama.stock.system.client.startup;

import com.kaibutsusama.stock.system.client.service.GrapClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 16:12
 */
@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.kaibutsusama"})
public class GrapClientApplication {

    @Autowired
    private GrapClientService grapClientService;

    public static void main(String[] args) {
        SpringApplication.run(GrapClientApplication.class,args);
    }


    @RequestMapping("/getStockPrice")
    public String StringgetStockPrice(@RequestParam(defaultValue = "我是股票名称") String name){
        return grapClientService.getStockPrice(name);
    }
}
