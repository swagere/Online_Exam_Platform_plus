package com.kve.dubbo_provider_email;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.kve")
public class DubboProviderEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderEmailApplication.class, args);
    }

}
