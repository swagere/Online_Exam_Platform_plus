package com.kve.dubbo_provider_login;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.kve")
public class DubboProviderLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderLoginApplication.class, args);
    }
}
