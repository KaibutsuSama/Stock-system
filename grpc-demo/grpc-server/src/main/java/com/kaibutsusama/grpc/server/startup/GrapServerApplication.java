package com.kaibutsusama.grpc.server.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 16:07
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kaibutsusama"})
public class GrapServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrapServerApplication.class,args);
    }
}
